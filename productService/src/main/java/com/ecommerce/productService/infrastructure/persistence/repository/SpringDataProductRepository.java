package com.ecommerce.productService.infrastructure.persistence.repository;

import com.ecommerce.productService.infrastructure.persistence.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataProductRepository extends JpaRepository<ProductEntity, Long> {
    boolean existsByName(String name);
}
