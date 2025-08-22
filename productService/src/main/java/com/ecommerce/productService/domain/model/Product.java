package com.ecommerce.productService.domain.model;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    private Long id;
    private String barcode;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stock;
    private Category category;
}
