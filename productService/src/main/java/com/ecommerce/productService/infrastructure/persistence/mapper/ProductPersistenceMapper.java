package com.ecommerce.productService.infrastructure.persistence.mapper;

import com.ecommerce.productService.domain.model.Product;
import com.ecommerce.productService.infrastructure.persistence.entity.ProductEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductPersistenceMapper {

    Product toDomain(ProductEntity entity);

    ProductEntity toEntity(Product domain);
}
