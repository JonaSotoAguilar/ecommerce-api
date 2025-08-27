package com.ecommerce.productservice.infrastructure.persistence.repository;

import com.ecommerce.productservice.infrastructure.persistence.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataCategoryRepository extends JpaRepository<CategoryEntity, Long> {
    boolean existsByName(String name);
}
