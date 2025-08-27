package com.ecommerce.productservice.application.mapper;

import com.ecommerce.productservice.application.dto.CategoryDto;
import com.ecommerce.productservice.domain.model.Category;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryDtoMapper {

    CategoryDto toDto(Category category);

    Category toDomain(CategoryDto req);

    List<CategoryDto> toDtoList(List<Category> categories);

    // Long -> Category (solo con id, sin ir a DB)
    default Category map(Long id) {
        return id != null ? Category.reference(id) : null;
    }

    // Category -> Long (extraer id)
//    default Long map(Category category) {
//        return category != null ? category.id() : null;
//    }
}