package com.ecommerce.productservice.infrastructure.persistence.adapter;

import com.ecommerce.productservice.domain.model.movement.Movement;
import com.ecommerce.productservice.domain.model.movement.MovementType;
import com.ecommerce.productservice.domain.port.MovementRepositoryPort;
import com.ecommerce.productservice.infrastructure.persistence.mapper.MovementDboMapper;
import com.ecommerce.productservice.infrastructure.persistence.repository.SpringDataMovementRepository;
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

    @Override
    public List<Movement> findAllByProduct(Long id) {
        return mapper.toDomainList(db.findByProduct_Id(id));
    }

    @Override
    public List<Movement> findAllByType(MovementType type) {
        return mapper.toDomainList(db.findByType(type));
    }
}
