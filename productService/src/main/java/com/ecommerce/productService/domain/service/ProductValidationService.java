package com.ecommerce.productService.domain.service;

import com.ecommerce.productService.domain.model.Product;
import org.springframework.util.StringUtils;

public class ProductValidationService {

    public static void validate(Product p) {
        if (!StringUtils.hasText(p.getName())) throw new IllegalArgumentException("name es obligatorio");
        if (p.getPrice() == null || p.getPrice().signum() < 0) throw new IllegalArgumentException("price inválido");
        if (p.getStock() == null || p.getStock() < 0)
            throw new IllegalArgumentException("stock inválido");
    }
}
