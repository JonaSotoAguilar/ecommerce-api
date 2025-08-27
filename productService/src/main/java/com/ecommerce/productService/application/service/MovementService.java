package com.ecommerce.productservice.application.service;

import com.ecommerce.productservice.application.constant.ErrorCode;
import com.ecommerce.productservice.application.dto.MovementDto;
import com.ecommerce.productservice.application.exception.BusinessException;
import com.ecommerce.productservice.application.mapper.MovementDtoMapper;
import com.ecommerce.productservice.application.usecase.MovementCrudCase;
import com.ecommerce.productservice.application.usecase.SearchMovementUseCase;
import com.ecommerce.productservice.domain.model.movement.Movement;
import com.ecommerce.productservice.domain.model.movement.MovementType;
import com.ecommerce.productservice.domain.port.MovementRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MovementService implements MovementCrudCase, SearchMovementUseCase {

    private final MovementRepositoryPort repo;
    private final MovementDtoMapper mapper;

    // --- CRUD Operations ---

    @Override
    @Transactional(readOnly = true)
    public MovementDto getById(Long id) {
        return mapper.toDto(getMovementOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<MovementDto> getAll() {
        return mapper.toDtoList(repo.findAll());
    }

    // -- Search Operations ---

    @Override
    public List<MovementDto> getAllByProduct(Long productId) {
        return mapper.toDtoList(repo.findAllByProduct(productId));
    }

    @Override
    public List<MovementDto> getAllByType(MovementType type) {
        return mapper.toDtoList(repo.findAllByType(type));
    }

    // -- Validation & Helpers ---

    private Movement getMovementOrThrow(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.MOVEMENT_NOT_FOUND));
    }

}
