package com.ecommerce.productservice.infrastructure.web.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record StockRequest(

        @Positive(message = "El id del producto debe ser un número positivo")
        @NotNull(message = "La cantidad es obligatorio")
        Integer quantity,

        @NotNull(message = "El costo unitario es obligatorio")
        @PositiveOrZero(message = "El costo unitario debe ser mayor o igual a 0")
        BigDecimal unitCost,

        @Size(max = 50, message = "La referencia no puede superar los 50 caracteres")
        String reference
) {
}
