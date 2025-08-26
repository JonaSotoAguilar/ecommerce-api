package com.ecommerce.productService.infrastructure.web.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CategoryRequest(

        @NotNull(message = "El nombre de categoría es obligatorio")
        @NotBlank(message = "El nombre es obligatorio")
        @Size(max = 120, message = "El nombre no puede superar los 120 caracteres")
        String name,

        @Size(max = 10000, message = "La descripción no puede superar los 10.000 caracteres")
        String description
) {
}