package com.ecommerce.productservice.infrastructure.persistence.mapper;

import com.ecommerce.productservice.domain.model.Category;
import com.ecommerce.productservice.infrastructure.persistence.entity.CategoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryDboMapper {
    Category toDomain(CategoryEntity entity);

    @Mappings({
            @Mapping(target = "createdAt", ignore = true),
            @Mapping(target = "updatedAt", ignore = true)
    })
    CategoryEntity toEntity(Category domain);

    List<Category> toDomainList(List<CategoryEntity> categories);
}
