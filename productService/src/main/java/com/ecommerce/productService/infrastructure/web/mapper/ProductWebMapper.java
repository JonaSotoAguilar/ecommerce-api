package com.ecommerce.productservice.infrastructure.web.mapper;

import com.ecommerce.productservice.application.dto.ProductDto;
import com.ecommerce.productservice.infrastructure.web.request.ProductRequest;
import com.ecommerce.productservice.infrastructure.web.response.ProductResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductWebMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "categoryName", ignore = true)
    ProductDto toDto(ProductRequest req);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "categoryName", ignore = true)
    ProductDto toDto(Long id, ProductRequest req);

    ProductResponse toResponse(ProductDto dto);

    List<ProductResponse> toResponseList(List<ProductDto> dtoList);
}
