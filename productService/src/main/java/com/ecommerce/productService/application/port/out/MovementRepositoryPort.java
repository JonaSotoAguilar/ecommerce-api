package com.ecommerce.productService.application.port.out;

import com.ecommerce.productService.domain.model.Movement;

import java.util.List;
import java.util.Optional;

public interface MovementRepositoryPort {
    Movement save(Movement product);

    Optional<Movement> findById(Long id);

    List<Movement> findAll();
}
