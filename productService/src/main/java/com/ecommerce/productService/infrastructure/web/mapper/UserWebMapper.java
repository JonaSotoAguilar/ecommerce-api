package com.ecommerce.productservice.infrastructure.web.mapper;

import com.ecommerce.productservice.application.dto.ProductDto;
import com.ecommerce.productservice.infrastructure.web.request.ProductRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserWebMapper {

    @Mapping(target = "id", ignore = true)
    ProductDto toDto(ProductRequest req);

    @Mapping(target = "id", source = "id")
    ProductDto toDto(Long id, ProductRequest req);
}
