package com.ecommerce.productService.application.port.in.query;

import com.ecommerce.productService.domain.model.dto.ProductDto;

import java.math.BigDecimal;
import java.util.List;

public interface ProductQueryUseCase {
    ProductDto getById(Long id);

    List<ProductDto> getAll();

    // Filters
    List<ProductDto> byCategory(Long categoryId);

    List<ProductDto> underPrice(BigDecimal maxPrice);

    List<ProductDto> underStock(Integer maxStock);
}
