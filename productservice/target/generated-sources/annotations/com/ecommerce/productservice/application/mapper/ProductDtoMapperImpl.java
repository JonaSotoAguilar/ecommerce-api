package com.ecommerce.productservice.application.mapper;

import com.ecommerce.productservice.application.dto.ProductDto;
import com.ecommerce.productservice.domain.model.Category;
import com.ecommerce.productservice.domain.model.product.Barcode;
import com.ecommerce.productservice.domain.model.product.Pricing;
import com.ecommerce.productservice.domain.model.product.Product;
import com.ecommerce.productservice.domain.model.product.Stock;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-09-01T17:16:27-0400",
    comments = "version: 1.6.2, compiler: javac, environment: Java 24.0.2 (Oracle Corporation)"
)
@Component
public class ProductDtoMapperImpl implements ProductDtoMapper {

    @Override
    public ProductDto toDto(Product domain) {
        if ( domain == null ) {
            return null;
        }

        String barcode = null;
        Integer stock = null;
        BigDecimal price = null;
        BigDecimal averageCost = null;
        Long categoryId = null;
        String categoryName = null;
        Long id = null;
        String name = null;
        String description = null;

        barcode = domainBarcodeValue( domain );
        stock = domainStockValue( domain );
        price = domainPricingPrice( domain );
        averageCost = domainPricingAverageCost( domain );
        categoryId = domainCategoryId( domain );
        categoryName = domainCategoryName( domain );
        id = domain.getId();
        name = domain.getName();
        description = domain.getDescription();

        ProductDto productDto = new ProductDto( id, barcode, name, description, price, averageCost, stock, categoryId, categoryName );

        return productDto;
    }

    @Override
    public Product toDomain(ProductDto dto) {
        if ( dto == null ) {
            return null;
        }

        Barcode barcode = null;
        Stock stock = null;
        Pricing pricing = null;
        Category category = null;
        Long id = null;
        String name = null;
        String description = null;

        barcode = productDtoToBarcode( dto );
        stock = productDtoToStock( dto );
        pricing = productDtoToPricing( dto );
        category = productDtoToCategory( dto );
        id = dto.id();
        name = dto.name();
        description = dto.description();

        Product product = new Product( id, barcode, name, description, pricing, stock, category );

        return product;
    }

    @Override
    public List<ProductDto> toDtoList(List<Product> products) {
        if ( products == null ) {
            return null;
        }

        List<ProductDto> list = new ArrayList<ProductDto>( products.size() );
        for ( Product product : products ) {
            list.add( toDto( product ) );
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

    private Integer domainStockValue(Product product) {
        Stock stock = product.getStock();
        if ( stock == null ) {
            return null;
        }
        return stock.value();
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

    private Long domainCategoryId(Product product) {
        Category category = product.getCategory();
        if ( category == null ) {
            return null;
        }
        return category.id();
    }

    private String domainCategoryName(Product product) {
        Category category = product.getCategory();
        if ( category == null ) {
            return null;
        }
        return category.name();
    }

    protected Barcode productDtoToBarcode(ProductDto productDto) {
        if ( productDto == null ) {
            return null;
        }

        String value = null;

        value = productDto.barcode();

        Barcode barcode = new Barcode( value );

        return barcode;
    }

    protected Stock productDtoToStock(ProductDto productDto) {
        if ( productDto == null ) {
            return null;
        }

        Integer value = null;

        value = productDto.stock();

        Stock stock = new Stock( value );

        return stock;
    }

    protected Pricing productDtoToPricing(ProductDto productDto) {
        if ( productDto == null ) {
            return null;
        }

        BigDecimal price = null;
        BigDecimal averageCost = null;

        price = productDto.price();
        averageCost = productDto.averageCost();

        Pricing pricing = new Pricing( price, averageCost );

        return pricing;
    }

    protected Category productDtoToCategory(ProductDto productDto) {
        if ( productDto == null ) {
            return null;
        }

        Long id = null;
        String name = null;

        id = productDto.categoryId();
        name = productDto.categoryName();

        String description = null;

        Category category = new Category( id, name, description );

        return category;
    }
}
