package com.ecommerce.productService.infrastructure.controller.mapper;

import com.ecommerce.productService.domain.model.Category;
import com.ecommerce.productService.infrastructure.controller.dto.CategoryRequest;
import com.ecommerce.productService.infrastructure.controller.dto.CategoryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryWebMapper {
    @Mapping(target = "id", ignore = true)
    Category toDomain(CategoryRequest req);

    CategoryResponse toResponse(Category category);
}