package com.ecommerce.productService.application.port.in;

import com.ecommerce.productService.domain.model.Category;

import java.util.List;

public interface CategoryUseCase {
    Category create(Category c);

    Category getById(Long id);

    List<Category> getAll();

    Category update(Long id, Category c);

    void delete(Long id);
}
