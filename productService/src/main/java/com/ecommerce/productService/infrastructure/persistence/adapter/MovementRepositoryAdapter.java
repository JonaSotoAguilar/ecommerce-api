package com.ecommerce.productService.infrastructure.persistence.adapter;

import com.ecommerce.productService.application.port.out.MovementRepositoryPort;
import com.ecommerce.productService.domain.model.Movement;
import com.ecommerce.productService.infrastructure.persistence.mapper.MovementDboMapper;
import com.ecommerce.productService.infrastructure.persistence.repository.SpringDataMovementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MovementRepositoryAdapter implements MovementRepositoryPort {

    private final SpringDataMovementRepository db;
    private final MovementDboMapper mapper;

    @Override
    public Movement save(Movement movement) {
        return mapper.toDomain(db.save(mapper.toDbo(movement)));
    }

    @Override
    public Optional<Movement> findById(Long id) {
        return db.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Movement> findAll() {
        return mapper.toDomainList(db.findAll());
    }
}
