package com.ecommerce.productservice.domain.model.movement;


import com.ecommerce.productservice.domain.model.product.Product;

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
