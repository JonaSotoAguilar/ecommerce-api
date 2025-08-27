package com.ecommerce.productservice.application.usecase;

import com.ecommerce.productservice.application.dto.ProductDto;

import java.math.BigDecimal;
import java.util.List;

public interface SearchProductsUseCase {
    List<ProductDto> getAllByCategory(Long categoryId);

    List<ProductDto> getAllUnderPrice(BigDecimal maxPrice);

    List<ProductDto> getAllUnderStock(Integer maxStock);
}
