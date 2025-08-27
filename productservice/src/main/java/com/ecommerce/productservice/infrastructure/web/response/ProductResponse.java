package com.ecommerce.productservice.infrastructure.web.response;

import java.math.BigDecimal;

public record ProductResponse(
        Long id,
        String barcode,
        String name,
        String description,
        BigDecimal price,
        BigDecimal averageCost,
        Integer stock,
        String categoryName
) {
}
