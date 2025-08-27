package com.ecommerce.productservice.infrastructure.web.request;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record ProductRequest(

        @NotNull(message = "El barcode de producto es obligatorio")
        @Pattern(
                regexp = "^(\\d{8}|\\d{12}|\\d{13}|\\d{14})$",    // GTIN: EAN-8, UPC-A (12), EAN-13, GTIN-14
                message = "El código de barras debe tener 8, 12, 13 o 14 dígitos"
        )
        String barcode,

        @NotNull(message = "El nombre de producto es obligatorio")
        @NotBlank(message = "El nombre es obligatorio")
        @Size(max = 50, message = "El nombre no puede superar los 50 caracteres")
        String name,

        @Size(max = 10_000, message = "La descripción no puede superar los 10.000 caracteres")
        String description,

        @NotNull(message = "El precio es obligatorio")
        @PositiveOrZero(message = "El precio debe ser mayor o igual a 0")
        BigDecimal price,

        @NotNull(message = "El costo promedio es obligatorio")
        @PositiveOrZero(message = "El costo promedio debe ser mayor o igual a 0")
        BigDecimal averageCost,

        @NotNull(message = "El stock es obligatorio")
        @PositiveOrZero(message = "El stock debe ser mayor o igual a 0")
        Integer stock,

        @Positive(message = "El id de la categoría debe ser positivo")
        Long categoryId
) {
}
