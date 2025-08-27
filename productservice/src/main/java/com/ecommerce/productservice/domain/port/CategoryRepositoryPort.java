package com.ecommerce.productservice.domain.port;

import com.ecommerce.productservice.domain.model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryRepositoryPort {
    Category save(Category category);

    Optional<Category> findById(Long id);

    List<Category> findAll();

    void deleteById(Long id);

    boolean existsByName(String name);

}
