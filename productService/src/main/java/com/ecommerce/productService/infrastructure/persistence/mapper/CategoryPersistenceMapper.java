package com.ecommerce.productService.infrastructure.persistence.mapper;

import com.ecommerce.productService.domain.model.Category;
import com.ecommerce.productService.infrastructure.persistence.entity.CategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryPersistenceMapper {
    Category toDomain(CategoryEntity entity);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    CategoryEntity toEntity(Category domain);
}
