package com.ecommerce.productService.application.mapper;

import com.ecommerce.productService.application.dto.MovementDto;
import com.ecommerce.productService.domain.event.StockAdjustedEvent;
import com.ecommerce.productService.domain.model.Movement;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ProductDtoMapper.class})
public interface MovementDtoMapper {

    @Mapping(source = "product.id", target = "productId")
    MovementDto toDto(Movement movement);

    List<MovementDto> toDtoList(List<Movement> movements);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "product", source = "productId")
    @Mapping(target = "movementDate", ignore = true)
    Movement toDomain(StockAdjustedEvent event);
}
