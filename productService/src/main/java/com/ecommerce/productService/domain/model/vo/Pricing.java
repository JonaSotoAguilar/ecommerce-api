package com.ecommerce.productService.domain.model.vo;

import java.math.BigDecimal;
import java.math.RoundingMode;

public record Pricing(BigDecimal price, BigDecimal averageCost) {
    public Pricing {
        if (price == null || averageCost == null)
            throw new IllegalArgumentException("price and averageCost cannot be null");
    }

    public Pricing updateAverageCost(BigDecimal newAverageCost) {
        if (newAverageCost == null || newAverageCost.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("New average cost cannot be null or negative");
        }
        return new Pricing(this.price, newAverageCost.setScale(4, RoundingMode.HALF_UP));
    }
}
