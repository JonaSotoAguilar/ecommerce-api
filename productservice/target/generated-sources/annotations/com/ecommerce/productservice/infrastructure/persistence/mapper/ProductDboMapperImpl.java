package com.ecommerce.productservice.infrastructure.persistence.mapper;

import com.ecommerce.productservice.domain.model.Category;
import com.ecommerce.productservice.domain.model.product.Barcode;
import com.ecommerce.productservice.domain.model.product.Pricing;
import com.ecommerce.productservice.domain.model.product.Product;
import com.ecommerce.productservice.domain.model.product.Stock;
import com.ecommerce.productservice.infrastructure.persistence.entity.ProductEntity;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-09-01T17:16:27-0400",
    comments = "version: 1.6.2, compiler: javac, environment: Java 24.0.2 (Oracle Corporation)"
)
@Component
public class ProductDboMapperImpl implements ProductDboMapper {

    @Autowired
    private CategoryDboMapper categoryDboMapper;

    @Override
    public ProductEntity toDbo(Product domain) {
        if ( domain == null ) {
            return null;
        }

        ProductEntity.ProductEntityBuilder productEntity = ProductEntity.builder();

        productEntity.barcode( domainBarcodeValue( domain ) );
        productEntity.price( domainPricingPrice( domain ) );
        productEntity.averageCost( domainPricingAverageCost( domain ) );
        productEntity.stock( domainStockValue( domain ) );
        productEntity.id( domain.getId() );
        productEntity.name( domain.getName() );
        productEntity.description( domain.getDescription() );
        productEntity.category( categoryDboMapper.toEntity( domain.getCategory() ) );

        return productEntity.build();
    }

    @Override
    public Product toDomain(ProductEntity entity) {
        if ( entity == null ) {
            return null;
        }

        Barcode barcode = null;
        Pricing pricing = null;
        Stock stock = null;
        Category category = null;
        Long id = null;
        String name = null;
        String description = null;

        barcode = productEntityToBarcode( entity );
        pricing = productEntityToPricing( entity );
        stock = productEntityToStock( entity );
        category = categoryDboMapper.toDomain( entity.getCategory() );
        id = entity.getId();
        name = entity.getName();
        description = entity.getDescription();

        Product product = new Product( id, barcode, name, description, pricing, stock, category );

        return product;
    }

    @Override
    public List<Product> toDomainList(List<ProductEntity> products) {
        if ( products == null ) {
            return null;
        }

        List<Product> list = new ArrayList<Product>( products.size() );
        for ( ProductEntity productEntity : products ) {
            list.add( toDomain( productEntity ) );
        }

        return list;
    }

    private String domainBarcodeValue(Product product) {
        Barcode barcode = product.getBarcode();
        if ( barcode == null ) {
            return null;
        }
        return barcode.value();
    }

    private BigDecimal domainPricingPrice(Product product) {
        Pricing pricing = product.getPricing();
        if ( pricing == null ) {
            return null;
        }
        return pricing.price();
    }

    private BigDecimal domainPricingAverageCost(Product product) {
        Pricing pricing = product.getPricing();
        if ( pricing == null ) {
            return null;
        }
        return pricing.averageCost();
    }

    private Integer domainStockValue(Product product) {
        Stock stock = product.getStock();
        if ( stock == null ) {
            return null;
        }
        return stock.value();
    }

    protected Barcode productEntityToBarcode(ProductEntity productEntity) {
        if ( productEntity == null ) {
            return null;
        }

        String value = null;

        value = productEntity.getBarcode();

        Barcode barcode = new Barcode( value );

        return barcode;
    }

    protected Pricing productEntityToPricing(ProductEntity productEntity) {
        if ( productEntity == null ) {
            return null;
        }

        BigDecimal price = null;
        BigDecimal averageCost = null;

        price = productEntity.getPrice();
        averageCost = productEntity.getAverageCost();

        Pricing pricing = new Pricing( price, averageCost );

        return pricing;
    }

    protected Stock productEntityToStock(ProductEntity productEntity) {
        if ( productEntity == null ) {
            return null;
        }

        Integer value = null;

        value = productEntity.getStock();

        Stock stock = new Stock( value );

        return stock;
    }
}
