package com.ecommerce.productService.domain.service;

import com.ecommerce.productService.domain.model.Category;
import org.springframework.util.StringUtils;

public class CategoryValidationService {

    public static void validate(Category c) {
        if (!StringUtils.hasText(c.getName())) throw new IllegalArgumentException("name es obligatorio");
    }
}
