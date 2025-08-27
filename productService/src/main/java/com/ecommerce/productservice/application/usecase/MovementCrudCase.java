package com.ecommerce.productservice.application.usecase;

import com.ecommerce.productservice.application.dto.MovementDto;

import java.util.List;

public interface MovementCrudCase {

    MovementDto getById(Long id);

    List<MovementDto> getAll();
}
