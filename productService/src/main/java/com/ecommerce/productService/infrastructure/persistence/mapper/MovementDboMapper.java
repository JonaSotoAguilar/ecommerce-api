package com.ecommerce.productService.infrastructure.persistence.mapper;

import com.ecommerce.productService.domain.model.Movement;
import com.ecommerce.productService.infrastructure.persistence.entity.MovementEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ProductDboMapper.class})
public interface MovementDboMapper {
    MovementEntity toDbo(Movement domain);

    Movement toDomain(MovementEntity entity);

    List<Movement> toDomainList(List<MovementEntity> movements);
}
