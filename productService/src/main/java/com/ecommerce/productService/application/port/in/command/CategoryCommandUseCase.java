package com.ecommerce.productService.application.port.in.command;

import com.ecommerce.productService.domain.model.dto.CategoryDto;
import com.ecommerce.productService.domain.model.dto.request.CategoryRequest;

public interface CategoryCommandUseCase {
    CategoryDto create(CategoryRequest c);

    CategoryDto update(Long id, CategoryRequest c);

    void delete(Long id);
}
