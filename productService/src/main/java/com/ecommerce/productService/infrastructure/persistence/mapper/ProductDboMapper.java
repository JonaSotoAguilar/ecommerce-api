package com.ecommerce.productService.infrastructure.persistence.mapper;

import com.ecommerce.productService.domain.model.Product;
import com.ecommerce.productService.infrastructure.persistence.entity.ProductEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {CategoryDboMapper.class})
public interface ProductDboMapper {

    @Mapping(target = "barcode", source = "barcode.value")
    @Mapping(target = "price", source = "pricing.price")
    @Mapping(target = "averageCost", source = "pricing.averageCost")
    @Mapping(target = "stock", source = "stock.value")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    ProductEntity toDbo(Product domain);

    @InheritInverseConfiguration
    Product toDomain(ProductEntity entity);

    List<Product> toDomainList(List<ProductEntity> products);
}
