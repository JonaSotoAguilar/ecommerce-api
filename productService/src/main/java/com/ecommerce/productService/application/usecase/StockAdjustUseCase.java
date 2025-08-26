package com.ecommerce.productService.application.usecase;

import java.math.BigDecimal;

public interface StockAdjustUseCase {
    void adjustStock(Long productId, int qty, BigDecimal unitCost, String reference);

    void receiveIn(Long productId, int qty, BigDecimal unitCost, String reference);

    void consumeOut(Long productId, int qty, String reference);

    void devolutionIn(Long productId, int qty, String reference);
}
