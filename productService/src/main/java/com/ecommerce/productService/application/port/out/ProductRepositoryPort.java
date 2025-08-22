package com.ecommerce.productService.application.port.out;

import com.ecommerce.productService.domain.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepositoryPort {
    Product save(Product product);

    Optional<Product> findById(Long id);

    Optional<Product> findByBarcode(String barcode);

    List<Product> findAll();

    void deleteById(Long id);

    boolean existsByName(String name);

    boolean existsByBarcode(String barcode);

    // Filters

    List<Product> findAllByCategoryId(Long categoryId);

    List<Product> findAllByPriceLessOrEqual(java.math.BigDecimal maxPrice);

    List<Product> findAllByStockLessOrEqual(Integer maxStock);
}
