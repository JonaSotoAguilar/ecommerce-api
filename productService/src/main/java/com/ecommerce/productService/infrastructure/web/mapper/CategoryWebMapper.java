package com.ecommerce.productService.infrastructure.web.mapper;

import com.ecommerce.productService.application.dto.CategoryDto;
import com.ecommerce.productService.infrastructure.web.request.CategoryRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryWebMapper {

    @Mapping(target = "id", ignore = true)
    CategoryDto toDto(CategoryRequest req);

    @Mapping(target = "id", source = "id")
    CategoryDto toDto(Long id, CategoryRequest req);
}
