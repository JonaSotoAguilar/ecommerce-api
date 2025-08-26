package com.ecommerce.productService.domain.port;

import com.ecommerce.productService.domain.model.Movement;

import java.util.List;
import java.util.Optional;

public interface MovementRepositoryPort {
    Movement save(Movement movement);

    Optional<Movement> findById(Long id);

    List<Movement> findAll();
}
