package com.ecommerce.productService.infrastructure.persistence.mapper;

import com.ecommerce.productService.domain.model.Category;
import com.ecommerce.productService.infrastructure.persistence.entity.CategoryEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryPersistenceMapper {
    Category toDomain(CategoryEntity e);

    CategoryEntity toEntity(Category d);
}
