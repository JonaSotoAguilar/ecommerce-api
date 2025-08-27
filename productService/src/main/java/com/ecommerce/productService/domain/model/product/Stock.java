package com.ecommerce.productservice.domain.model.product;

public record Stock(Integer value) {
    public Stock {
        if (value < 0) throw new IllegalArgumentException("stock cannot be negative");
    }

    public Stock add(int qty) {
        if (qty <= 0) throw new IllegalArgumentException("quantity to add must be positive");
        return new Stock(value + qty);
    }

    public Stock subtract(int qty) {
        if (qty <= 0 || qty > value) throw new IllegalArgumentException("invalid quantity to remove");
        return new Stock(value - qty);
    }
}
