package com.ecommerce.productService.infrastructure.persistence.repository;

import com.ecommerce.productService.infrastructure.persistence.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataCategoryRepository extends JpaRepository<CategoryEntity, Long> {
    boolean existsByName(String name);
}
