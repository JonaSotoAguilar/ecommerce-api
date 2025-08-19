package com.ecommerce.productService.infrastructure.controller.mapper;

import com.ecommerce.productService.domain.model.Product;
import com.ecommerce.productService.infrastructure.controller.dto.ProductRequest;
import com.ecommerce.productService.infrastructure.controller.dto.ProductResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductWebMapper {

    @Mapping(target = "id", ignore = true)
    Product toDomain(ProductRequest request);

    ProductResponse toResponse(Product product);
}
