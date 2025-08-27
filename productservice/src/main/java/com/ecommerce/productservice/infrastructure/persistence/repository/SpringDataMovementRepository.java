package com.ecommerce.productservice.infrastructure.persistence.repository;

import com.ecommerce.productservice.domain.model.movement.MovementType;
import com.ecommerce.productservice.infrastructure.persistence.entity.MovementEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpringDataMovementRepository extends JpaRepository<MovementEntity, Long> {
    List<MovementEntity> findByType(MovementType type);

    List<MovementEntity> findByProduct_Id(Long id);
}
