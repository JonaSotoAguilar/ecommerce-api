package com.ecommerce.productService.application.usecase;

import com.ecommerce.productService.application.dto.ProductDto;

import java.math.BigDecimal;
import java.util.List;

public interface SearchProductsUseCase {
    List<ProductDto> byCategory(Long categoryId);

    List<ProductDto> underPrice(BigDecimal maxPrice);

    List<ProductDto> underStock(Integer maxStock);
}
