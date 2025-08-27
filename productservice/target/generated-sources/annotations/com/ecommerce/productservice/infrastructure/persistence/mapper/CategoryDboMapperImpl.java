package com.ecommerce.productservice.infrastructure.persistence.mapper;

import com.ecommerce.productservice.domain.model.Category;
import com.ecommerce.productservice.infrastructure.persistence.entity.CategoryEntity;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-27T15:16:00-0400",
    comments = "version: 1.6.2, compiler: javac, environment: Java 24.0.2 (Oracle Corporation)"
)
@Component
public class CategoryDboMapperImpl implements CategoryDboMapper {

    @Override
    public Category toDomain(CategoryEntity entity) {
        if ( entity == null ) {
            return null;
        }

        Long id = null;
        String name = null;
        String description = null;

        id = entity.getId();
        name = entity.getName();
        description = entity.getDescription();

        Category category = new Category( id, name, description );

        return category;
    }

    @Override
    public CategoryEntity toEntity(Category domain) {
        if ( domain == null ) {
            return null;
        }

        CategoryEntity.CategoryEntityBuilder categoryEntity = CategoryEntity.builder();

        categoryEntity.id( domain.id() );
        categoryEntity.name( domain.name() );
        categoryEntity.description( domain.description() );

        return categoryEntity.build();
    }

    @Override
    public List<Category> toDomainList(List<CategoryEntity> categories) {
        if ( categories == null ) {
            return null;
        }

        List<Category> list = new ArrayList<Category>( categories.size() );
        for ( CategoryEntity categoryEntity : categories ) {
            list.add( toDomain( categoryEntity ) );
        }

        return list;
    }
}
