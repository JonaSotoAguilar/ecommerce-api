package com.ecommerce.productService.application.usecase;

import com.ecommerce.productService.application.dto.MovementDto;

import java.util.List;

public interface MovementCrudCase {

    MovementDto getById(Long id);

    List<MovementDto> getAll();
}
