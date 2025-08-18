package com.ecommerce.productService.infrastructure.persistence.mapper;

import com.ecommerce.productService.domain.model.Product;
import com.ecommerce.productService.infrastructure.controller.dto.ProductRequest;
import com.ecommerce.productService.infrastructure.controller.dto.ProductResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductWebMapper {

    // DTO -> Domain (para crear/actualizar)
    @Mapping(target = "id", ignore = true)
    Product toDomain(ProductRequest request);

    // Domain -> DTO (respuesta)
    ProductResponse toResponse(Product product);
}
