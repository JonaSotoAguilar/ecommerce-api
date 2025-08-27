package com.ecommerce.productservice.application.mapper;

import com.ecommerce.productservice.application.dto.ProductDto;
import com.ecommerce.productservice.domain.model.product.Product;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {CategoryDtoMapper.class})
public interface ProductDtoMapper {

    @Mapping(target = "barcode", source = "barcode.value")
    @Mapping(target = "stock", source = "stock.value")
    @Mapping(target = "price", source = "pricing.price")
    @Mapping(target = "averageCost", source = "pricing.averageCost")
    @Mapping(target = "categoryId", source = "category.id")
    @Mapping(target = "categoryName", source = "category.name")
    ProductDto toDto(Product domain);

    @InheritInverseConfiguration
    Product toDomain(ProductDto dto);

    List<ProductDto> toDtoList(List<Product> products);

    // Long -> Product (solo con id, sin ir a DB)
    default Product map(Long id) {
        return (id == null) ? null : Product.reference(id);
    }

    // Product -> Long (extraer id)
//    default Long map(Product product) {
//        return product != null ? product.getId() : null;
//    }
}

