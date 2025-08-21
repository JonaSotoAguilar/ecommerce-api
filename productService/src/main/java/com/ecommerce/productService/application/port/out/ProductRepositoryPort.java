package com.ecommerce.productService.application.port.out;

import com.ecommerce.productService.domain.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepositoryPort {
    Product save(Product product);

    Optional<Product> findById(Long id);

    List<Product> findAll();

    void deleteById(Long id);

    boolean existsByName(String name);

    // Filters

    List<Product> findAllByCategoryId(Long categoryId);

    List<Product> findAllByPriceLessOrEqual(java.math.BigDecimal maxPrice);

    List<Product> findAllByStockLessOrEqual(Integer maxStock);
}
