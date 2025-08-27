package com.ecommerce.productservice.domain.model;

public record Category(
        Long id,
        String name,
        String description) {

    public static Category reference(Long id) {
        if (id == null) throw new IllegalArgumentException("id null");
        return new Category(id, null, null);
    }
}
