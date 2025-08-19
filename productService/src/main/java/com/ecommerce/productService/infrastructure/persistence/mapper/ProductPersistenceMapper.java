package com.ecommerce.productService.infrastructure.persistence.mapper;

import com.ecommerce.productService.domain.model.Product;
import com.ecommerce.productService.infrastructure.persistence.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductPersistenceMapper {
    Product toDomain(ProductEntity entity);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    ProductEntity toEntity(Product domain);
}
