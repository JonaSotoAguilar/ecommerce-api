package com.ecommerce.productservice.application.mapper;

import com.ecommerce.productservice.application.dto.CategoryDto;
import com.ecommerce.productservice.domain.model.Category;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-27T15:45:45-0400",
    comments = "version: 1.6.2, compiler: javac, environment: Java 24.0.2 (Oracle Corporation)"
)
@Component
public class CategoryDtoMapperImpl implements CategoryDtoMapper {

    @Override
    public CategoryDto toDto(Category category) {
        if ( category == null ) {
            return null;
        }

        Long id = null;
        String name = null;
        String description = null;

        id = category.id();
        name = category.name();
        description = category.description();

        CategoryDto categoryDto = new CategoryDto( id, name, description );

        return categoryDto;
    }

    @Override
    public Category toDomain(CategoryDto req) {
        if ( req == null ) {
            return null;
        }

        Long id = null;
        String name = null;
        String description = null;

        id = req.id();
        name = req.name();
        description = req.description();

        Category category = new Category( id, name, description );

        return category;
    }

    @Override
    public List<CategoryDto> toDtoList(List<Category> categories) {
        if ( categories == null ) {
            return null;
        }

        List<CategoryDto> list = new ArrayList<CategoryDto>( categories.size() );
        for ( Category category : categories ) {
            list.add( toDto( category ) );
        }

        return list;
    }
}
