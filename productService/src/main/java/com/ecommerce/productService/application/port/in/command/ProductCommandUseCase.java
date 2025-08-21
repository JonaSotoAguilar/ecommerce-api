package com.ecommerce.productService.application.port.in.command;

import com.ecommerce.productService.domain.model.dto.ProductDto;
import com.ecommerce.productService.domain.model.dto.request.ProductRequest;

public interface ProductCommandUseCase {
    ProductDto create(ProductRequest product);

    ProductDto update(Long id, ProductRequest product);

    void delete(Long id);
}
