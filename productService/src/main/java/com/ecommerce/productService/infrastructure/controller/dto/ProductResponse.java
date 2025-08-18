package com.ecommerce.productService.infrastructure.controller.dto;

import java.math.BigDecimal;

public record ProductResponse(
        Long id, String name, String description, BigDecimal price, Integer stockQuantity
) {
}
