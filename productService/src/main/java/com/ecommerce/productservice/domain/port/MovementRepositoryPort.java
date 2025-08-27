package com.ecommerce.productservice.domain.port;

import com.ecommerce.productservice.domain.model.movement.Movement;
import com.ecommerce.productservice.domain.model.movement.MovementType;

import java.util.List;
import java.util.Optional;

public interface MovementRepositoryPort {
    Movement save(Movement movement);

    Optional<Movement> findById(Long id);

    List<Movement> findAll();

    List<Movement> findAllByProduct(Long id);

    List<Movement> findAllByType(MovementType type);
}
