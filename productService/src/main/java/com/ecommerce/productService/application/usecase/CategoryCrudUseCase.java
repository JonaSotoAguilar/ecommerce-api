package com.ecommerce.productService.application.usecase;

import com.ecommerce.productService.application.dto.CategoryDto;

import java.util.List;

public interface CategoryCrudUseCase {

    CategoryDto create(CategoryDto c);

    CategoryDto getById(Long id);

    List<CategoryDto> getAll();

    CategoryDto update(CategoryDto c);

    void delete(Long id);
}
