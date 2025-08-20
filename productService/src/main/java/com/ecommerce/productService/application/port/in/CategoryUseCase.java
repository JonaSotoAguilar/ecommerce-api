package com.ecommerce.productService.application.port.in;

import com.ecommerce.productService.domain.model.dto.CategoryDto;
import com.ecommerce.productService.domain.model.dto.request.CategoryRequest;

import java.util.List;

public interface CategoryUseCase {
    CategoryDto create(CategoryRequest c);

    CategoryDto getById(Long id);

    List<CategoryDto> getAll();

    CategoryDto update(Long id, CategoryRequest c);

    void delete(Long id);
}
