package com.ecommerce.productService.application.port.in;

import com.ecommerce.productService.domain.model.dto.ProductDto;
import com.ecommerce.productService.domain.model.dto.request.ProductRequest;

import java.util.List;

public interface ProductUseCase {
    ProductDto create(ProductRequest product);

    ProductDto getById(Long id);

    List<ProductDto> getAll();

    ProductDto update(Long id, ProductRequest product);

    void delete(Long id);
}
