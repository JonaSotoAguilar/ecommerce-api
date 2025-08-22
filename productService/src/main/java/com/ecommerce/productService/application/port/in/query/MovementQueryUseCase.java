package com.ecommerce.productService.application.port.in.query;

import com.ecommerce.productService.domain.model.dto.MovementDto;

import java.util.List;

public interface MovementQueryUseCase {
    MovementDto getById(Long id);

    List<MovementDto> getAll();
}
