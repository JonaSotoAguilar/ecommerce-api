package com.ecommerce.productService.domain.event;

import com.ecommerce.productService.domain.model.constant.MovementType;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record StockAdjustedEvent(
        MovementType type,
        Integer stockBefore,
        Integer quantity,
        BigDecimal averageCostBefore,
        BigDecimal unitCost,
        String reference,
        String productNameSnapshot,
        Long productId,
        OffsetDateTime occurredAt
) implements DomainEvent {
}
