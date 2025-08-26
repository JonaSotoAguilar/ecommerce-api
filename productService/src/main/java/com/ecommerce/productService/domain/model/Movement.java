package com.ecommerce.productService.domain.model;


import com.ecommerce.productService.domain.model.constant.MovementType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record Movement(
        Long id,
        MovementType type,
        Integer stockBefore,
        Integer quantity,
        BigDecimal averageCostBefore,
        BigDecimal unitCost,
        String reference,
        String productNameSnapshot,
        LocalDateTime movementDate,
        Product product) {
}
