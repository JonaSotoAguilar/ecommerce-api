package com.ecommerce.productservice.application.usecase;

import com.ecommerce.productservice.application.dto.MovementDto;
import com.ecommerce.productservice.domain.model.movement.MovementType;

import java.util.List;

public interface SearchMovementUseCase {

    List<MovementDto> getAllByProduct(Long productId);

    List<MovementDto> getAllByType(MovementType type);
}
