package com.ecommerce.productService.domain.model.dto.request;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record ProductRequest(

        @NotNull(message = "El barcode de producto es obligatorio")
        @Pattern(
                regexp = "^(\\d{8}|\\d{12}|\\d{13}|\\d{14})$",    // GTIN: EAN-8, UPC-A (12), EAN-13, GTIN-14
                message = "El código de barras debe tener 8, 12, 13 o 14 dígitos"
        )
        String barcode,

        @NotBlank(message = "El nombre es obligatorio")
        @Size(max = 50, message = "El nombre no puede superar los 50 caracteres")
        String name,

        @Size(max = 10_000, message = "La descripción no puede superar los 10.000 caracteres")
        String description,

        @NotNull(message = "El precio es obligatorio")
        @DecimalMin(value = "0.0", message = "El precio no puede ser negativo")
        BigDecimal price,

        @NotNull(message = "El stock es obligatorio")
        @Min(value = 0, message = "El stock no puede ser negativo")
        Integer stock,

        @Positive(message = "El id de la categoría debe ser positivo")
        Long categoryId
) {
}
