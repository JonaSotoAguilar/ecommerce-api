package com.ecommerce.productService.application.usecase;

import com.ecommerce.productService.application.dto.ProductDto;

import java.util.List;

public interface ProductCrudUseCase {
    ProductDto create(ProductDto product);

    ProductDto getById(Long id);

    ProductDto getByBarcode(String barcode);

    List<ProductDto> getAll();

    ProductDto update(ProductDto product);

    void delete(Long id);
}
