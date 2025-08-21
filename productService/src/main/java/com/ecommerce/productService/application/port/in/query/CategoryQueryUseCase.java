package com.ecommerce.productService.application.port.in.query;

import com.ecommerce.productService.domain.model.dto.CategoryDto;

import java.util.List;

public interface CategoryQueryUseCase {

    CategoryDto getById(Long id);

    List<CategoryDto> getAll();
}
