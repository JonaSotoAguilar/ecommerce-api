package com.ecommerce.productService.application.mapper;

import com.ecommerce.productService.domain.model.Movement;
import com.ecommerce.productService.domain.model.dto.MovementDto;
import com.ecommerce.productService.domain.model.dto.request.MovementRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ProductDtoMapper.class})
public interface MovementDtoMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(source = "productId", target = "product")
    Movement toDomain(MovementRequest req);

    @Mapping(source = "product", target = "productId")
    MovementDto toDto(Movement movement);

    List<MovementDto> toDtoList(List<Movement> movements);
}
