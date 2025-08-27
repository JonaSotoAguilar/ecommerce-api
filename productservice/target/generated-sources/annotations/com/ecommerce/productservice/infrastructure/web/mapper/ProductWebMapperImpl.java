package com.ecommerce.productservice.infrastructure.web.mapper;

import com.ecommerce.productservice.application.dto.ProductDto;
import com.ecommerce.productservice.infrastructure.web.request.ProductRequest;
import com.ecommerce.productservice.infrastructure.web.response.ProductResponse;
import java.math.BigDecimal;
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
public class ProductWebMapperImpl implements ProductWebMapper {

    @Override
    public ProductDto toDto(ProductRequest req) {
        if ( req == null ) {
            return null;
        }

        String barcode = null;
        String name = null;
        String description = null;
        BigDecimal price = null;
        BigDecimal averageCost = null;
        Integer stock = null;
        Long categoryId = null;

        barcode = req.barcode();
        name = req.name();
        description = req.description();
        price = req.price();
        averageCost = req.averageCost();
        stock = req.stock();
        categoryId = req.categoryId();

        Long id = null;
        String categoryName = null;

        ProductDto productDto = new ProductDto( id, barcode, name, description, price, averageCost, stock, categoryId, categoryName );

        return productDto;
    }

    @Override
    public ProductDto toDto(Long id, ProductRequest req) {
        if ( id == null && req == null ) {
            return null;
        }

        String barcode = null;
        String name = null;
        String description = null;
        BigDecimal price = null;
        BigDecimal averageCost = null;
        Integer stock = null;
        Long categoryId = null;
        if ( req != null ) {
            barcode = req.barcode();
            name = req.name();
            description = req.description();
            price = req.price();
            averageCost = req.averageCost();
            stock = req.stock();
            categoryId = req.categoryId();
        }
        Long id1 = null;
        id1 = id;

        String categoryName = null;

        ProductDto productDto = new ProductDto( id1, barcode, name, description, price, averageCost, stock, categoryId, categoryName );

        return productDto;
    }

    @Override
    public ProductResponse toResponse(ProductDto dto) {
        if ( dto == null ) {
            return null;
        }

        Long id = null;
        String barcode = null;
        String name = null;
        String description = null;
        BigDecimal price = null;
        BigDecimal averageCost = null;
        Integer stock = null;
        String categoryName = null;

        id = dto.id();
        barcode = dto.barcode();
        name = dto.name();
        description = dto.description();
        price = dto.price();
        averageCost = dto.averageCost();
        stock = dto.stock();
        categoryName = dto.categoryName();

        ProductResponse productResponse = new ProductResponse( id, barcode, name, description, price, averageCost, stock, categoryName );

        return productResponse;
    }

    @Override
    public List<ProductResponse> toResponseList(List<ProductDto> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<ProductResponse> list = new ArrayList<ProductResponse>( dtoList.size() );
        for ( ProductDto productDto : dtoList ) {
            list.add( toResponse( productDto ) );
        }

        return list;
    }
}
