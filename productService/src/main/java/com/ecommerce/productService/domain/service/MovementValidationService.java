package com.ecommerce.productService.domain.service;

import com.ecommerce.productService.domain.model.Movement;

public class MovementValidationService {

    public static void validate(Movement m) {
        if (m.getType() == null)
            throw new IllegalArgumentException("type inválido");
        if (m.getQuantity() == null || m.getQuantity() <= 0)
            throw new IllegalArgumentException("quantity inválido");
        if (m.getProduct() == null)
            throw new IllegalArgumentException("product inválido");
    }
}
