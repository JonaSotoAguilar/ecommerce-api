package com.ecommerce.productService.application.dto;

import java.math.BigDecimal;

public record ProductDto(
        Long id,
        String barcode,
        String name,
        String description,
        BigDecimal price,
        BigDecimal averageCost,
        Integer stock,
        Long categoryId
) {
}
