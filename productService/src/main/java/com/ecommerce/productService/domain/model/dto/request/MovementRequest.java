package com.ecommerce.productService.domain.model.dto.request;

import com.ecommerce.productService.domain.model.constant.MovementType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record MovementRequest(
        MovementType type,

        @NotNull(message = "La cantidad es obligatoria")
        @Min(value = 1, message = "La cantidad mínima es 1")
        Integer quantity,

        @Size(max = 50, message = "La nota no puede superar los 50 caracteres")
        String note,

        @NotNull(message = "El producto es obligatorio")
        @Positive(message = "El id del producto debe ser positivo")
        Long productId
) {
}
