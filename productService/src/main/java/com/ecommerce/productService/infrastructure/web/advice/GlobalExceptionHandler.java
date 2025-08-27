package com.ecommerce.productservice.infrastructure.web.advice;

import com.ecommerce.productservice.application.constant.ErrorCode;
import com.ecommerce.productservice.application.exception.BusinessException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 400 - BusinessException
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiError> handleBusiness(BusinessException ex, HttpServletRequest req) {
        var ec = ex.getCode();
        return build(HttpStatus.valueOf(ec.http), "Error de negocio", ec.name(),
                ex.getMessage(), req.getRequestURI(), List.of());
    }

    // 400 - Bean Validation (@Valid en @RequestBody)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest req) {
        var details = ex.getBindingResult().getFieldErrors().stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .toList();
        return build(HttpStatus.BAD_REQUEST, "Validación fallida",
                ErrorCode.VALIDATION_FAILED.name(),
                "Se encontraron errores de validación", req.getRequestURI(), details);
    }

    // 400 - Bean Validation en @PathVariable/@RequestParam (@Positive, etc.)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiError> handleConstraint(ConstraintViolationException ex, HttpServletRequest req) {
        var details = ex.getConstraintViolations().stream()
                .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                .toList();
        return build(HttpStatus.BAD_REQUEST, "Parámetros inválidos",
                ErrorCode.VALIDATION_FAILED.name(),
                "Se encontraron errores en parámetros", req.getRequestURI(), details);
    }

    // 400 - Ej.: enum inválido en @PathVariable MovementType
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiError> handleTypeMismatch(MethodArgumentTypeMismatchException ex, HttpServletRequest req) {
        String detail = "Valor inválido para '" + ex.getName() + "'";
        if (ex.getRequiredType() != null && ex.getRequiredType().isEnum()) {
            String allowed = Arrays.stream(ex.getRequiredType().getEnumConstants()).map(Object::toString).collect(Collectors.joining(", "));
            detail += " (valores permitidos: " + allowed + ")";
        }
        return build(HttpStatus.BAD_REQUEST, "Tipo de argumento inválido",
                ErrorCode.VALIDATION_FAILED.name(), detail, req.getRequestURI(), List.of());
    }

    // 404 - cuando JPA no encuentra una entidad lazily referenciada (soft delete, etc.)
    @ExceptionHandler(JpaObjectRetrievalFailureException.class)
    public ResponseEntity<ApiError> handleJpaObjectNotFound(JpaObjectRetrievalFailureException ex, HttpServletRequest req) {
        return build(HttpStatus.NOT_FOUND, "Recurso no encontrado",
                ErrorCode.PRODUCT_NOT_FOUND.name(), ex.getMostSpecificCause().getMessage(),
                req.getRequestURI(), List.of());
    }

    // 409 - claves únicas / FK
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiError> handleIntegrity(DataIntegrityViolationException ex, HttpServletRequest req) {
        // Mapea por nombre de índice o mensaje para dar códigos específicos
        var msg = ex.getMostSpecificCause() != null ? ex.getMostSpecificCause().getMessage() : ex.getMessage();
        var code = msg != null && msg.contains("UK_product_name") ? ErrorCode.PRODUCT_NAME_DUPLICATED
                : msg != null && msg.contains("UK_product_barcode") ? ErrorCode.PRODUCT_BARCODE_DUPLICATED
                : null;

        if (code != null) {
            return build(HttpStatus.CONFLICT, "Violación de integridad de datos",
                    code.name(), code.defaultMessage, req.getRequestURI(), List.of());
        }
        return build(HttpStatus.CONFLICT, "Violación de integridad de datos",
                "DATA_INTEGRITY", "La operación viola restricciones de datos", req.getRequestURI(), List.of());
    }

    // 500 - fallback
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGeneric(Exception ex, HttpServletRequest req) {
        return build(HttpStatus.INTERNAL_SERVER_ERROR, "Error interno",
                "INTERNAL_ERROR", "Ha ocurrido un error inesperado", req.getRequestURI(), List.of());
    }

    private ResponseEntity<ApiError> build(HttpStatus status, String error, String code,
                                           String message, String path, List<String> errors) {
        var body = new ApiError(OffsetDateTime.now(), status.value(), error, code, message, path, errors);
        return ResponseEntity.status(status).body(body);
    }
}
