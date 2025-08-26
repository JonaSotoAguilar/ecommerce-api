package com.ecommerce.productService.application.service;

import com.ecommerce.productService.application.dto.MovementDto;
import com.ecommerce.productService.application.mapper.MovementDtoMapper;
import com.ecommerce.productService.application.usecase.MovementCrudCase;
import com.ecommerce.productService.domain.port.MovementRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MovementService implements MovementCrudCase {

    private final MovementRepositoryPort repo;
    private final MovementDtoMapper mapper;

    // --- CRUD Operations ---

    @Override
    @Transactional(readOnly = true)
    public MovementDto getById(Long id) {
        return repo.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new IllegalArgumentException("Movimiento no encontrado"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<MovementDto> getAll() {
        return mapper.toDtoList(repo.findAll());
    }

}
