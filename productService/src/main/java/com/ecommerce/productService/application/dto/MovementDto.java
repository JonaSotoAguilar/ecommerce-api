package com.ecommerce.productService.application.dto;

import com.ecommerce.productService.domain.model.constant.MovementType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record MovementDto(
        Long id,
        MovementType type,
        Integer stockBefore,
        Integer quantity,
        BigDecimal averageCostBefore,
        BigDecimal unitCost,
        String reference,
        String productNameSnapshot,
        Long productId,
        LocalDateTime movementDate
) {
}
