package com.ecommerce.productService.application.service.query;

import com.ecommerce.productService.application.mapper.MovementDtoMapper;
import com.ecommerce.productService.application.port.in.query.MovementQueryUseCase;
import com.ecommerce.productService.application.port.out.MovementRepositoryPort;
import com.ecommerce.productService.domain.model.dto.MovementDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovementQueryService implements MovementQueryUseCase {

    private final MovementRepositoryPort repo;
    private final MovementDtoMapper mapper;

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
