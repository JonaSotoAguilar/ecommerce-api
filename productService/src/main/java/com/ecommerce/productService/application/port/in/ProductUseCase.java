package com.ecommerce.productService.application.port.in;

import com.ecommerce.productService.domain.model.Product;

import java.util.List;

public interface ProductUseCase {
    Product create(Product product);
    Product getById(Long id);
    List<Product> getAll();
    Product update(Long id, Product product);
    void delete(Long id);
}
