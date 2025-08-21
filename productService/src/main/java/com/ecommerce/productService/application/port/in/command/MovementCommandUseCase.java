package com.ecommerce.productService.application.port.in.command;

import com.ecommerce.productService.domain.model.dto.MovementDto;
import com.ecommerce.productService.domain.model.dto.request.MovementRequest;

public interface MovementCommandUseCase {
    MovementDto create(MovementRequest c);
}
