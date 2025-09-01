package com.ecommerce.productservice.infrastructure.web.mapper;

import com.ecommerce.productservice.application.dto.CategoryDto;
import com.ecommerce.productservice.infrastructure.web.request.CategoryRequest;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-09-01T17:16:27-0400",
    comments = "version: 1.6.2, compiler: javac, environment: Java 24.0.2 (Oracle Corporation)"
)
@Component
public class CategoryWebMapperImpl implements CategoryWebMapper {

    @Override
    public CategoryDto toDto(CategoryRequest req) {
        if ( req == null ) {
            return null;
        }

        String name = null;
        String description = null;

        name = req.name();
        description = req.description();

        Long id = null;

        CategoryDto categoryDto = new CategoryDto( id, name, description );

        return categoryDto;
    }

    @Override
    public CategoryDto toDto(Long id, CategoryRequest req) {
        if ( id == null && req == null ) {
            return null;
        }

        String name = null;
        String description = null;
        if ( req != null ) {
            name = req.name();
            description = req.description();
        }
        Long id1 = null;
        id1 = id;

        CategoryDto categoryDto = new CategoryDto( id1, name, description );

        return categoryDto;
    }
}
