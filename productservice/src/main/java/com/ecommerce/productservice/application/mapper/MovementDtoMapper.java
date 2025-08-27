package com.ecommerce.productservice.application.mapper;

import com.ecommerce.productservice.application.dto.MovementDto;
import com.ecommerce.productservice.domain.event.StockAdjustedEvent;
import com.ecommerce.productservice.domain.model.movement.Movement;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ProductDtoMapper.class})
public interface MovementDtoMapper {

    @Mapping(target = "productId", source = "product.id")
    MovementDto toDto(Movement movement);

    List<MovementDto> toDtoList(List<Movement> movements);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "product", source = "productId")
    @Mapping(target = "movementDate", ignore = true)
    Movement toDomain(StockAdjustedEvent event);
}
