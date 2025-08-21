package com.ecommerce.productService.application.mapper;

import com.ecommerce.productService.domain.model.Product;
import com.ecommerce.productService.domain.model.dto.ProductDto;
import com.ecommerce.productService.domain.model.dto.request.ProductRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {CategoryDtoMapper.class})
public interface ProductDtoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "categoryId", target = "category")
    Product toDomain(ProductRequest request);

    @Mapping(source = "category", target = "categoryId")
    ProductDto toDto(Product product);

    List<ProductDto> toDtoList(List<Product> products);

    // Long -> Product (solo con id, sin ir a DB)
    default Product map(Long id) {
        if (id == null) return null;
        Product p = new Product();
        p.setId(id);
        return p;
    }

    // Product -> Long (extraer id)
    default Long map(Product product) {
        return product != null ? product.getId() : null;
    }
}

