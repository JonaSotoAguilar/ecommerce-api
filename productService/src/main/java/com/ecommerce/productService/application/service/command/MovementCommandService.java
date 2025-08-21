package com.ecommerce.productService.application.service.command;

import com.ecommerce.productService.application.mapper.MovementDtoMapper;
import com.ecommerce.productService.application.port.in.command.MovementCommandUseCase;
import com.ecommerce.productService.application.port.out.MovementRepositoryPort;
import com.ecommerce.productService.application.port.out.ProductRepositoryPort;
import com.ecommerce.productService.domain.model.Movement;
import com.ecommerce.productService.domain.model.dto.MovementDto;
import com.ecommerce.productService.domain.model.dto.request.MovementRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.ecommerce.productService.domain.service.MovementValidationService.validate;

@Service
@RequiredArgsConstructor
public class MovementCommandService implements MovementCommandUseCase {

    private final MovementRepositoryPort repo;
    private final ProductRepositoryPort productRepo;
    private final MovementDtoMapper mapper;

    @Override
    @Transactional
    public MovementDto create(MovementRequest request) {
        Movement movementDomain = mapper.toDomain(request);

        validate(movementDomain);
        productRepo.findById(movementDomain.getProduct().getId())
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));

        return mapper.toDto(repo.save(movementDomain));
    }

}
