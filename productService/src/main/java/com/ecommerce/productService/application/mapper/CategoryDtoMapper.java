package com.ecommerce.productService.application.mapper;

import com.ecommerce.productService.domain.model.Category;
import com.ecommerce.productService.domain.model.dto.CategoryDto;
import com.ecommerce.productService.domain.model.dto.request.CategoryRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryDtoMapper {

    @Mapping(target = "id", ignore = true)
    Category toDomain(CategoryRequest req);

    CategoryDto toDto(Category category);

    List<CategoryDto> toDtoList(List<Category> categories);

    // Long -> Category (solo con id, sin ir a DB)
    default Category map(Long id) {
        if (id == null) return null;
        Category c = new Category();
        c.setId(id);
        return c;
    }

    // Category -> Long (extraer id)
    default Long map(Category category) {
        return category != null ? category.getId() : null;
    }
}