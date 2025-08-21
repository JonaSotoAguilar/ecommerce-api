package com.ecommerce.productService.infrastructure.persistence.repository;

import com.ecommerce.productService.infrastructure.persistence.entity.MovementEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataMovementRepository extends JpaRepository<MovementEntity, Long> {
}
