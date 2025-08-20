package com.ecommerce.productService.domain.model.dto.request;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record ProductRequest(
        @NotBlank @Size(max = 150) String name,
        @Size(max = 10_000) String description,
        @NotNull @DecimalMin(value = "0.0") BigDecimal price,
        @NotNull @Min(0) Integer stock,
        Long categoryId
) {
}
