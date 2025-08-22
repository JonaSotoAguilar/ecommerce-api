package com.ecommerce.productService.infrastructure.persistence.repository;

import com.ecommerce.productService.infrastructure.persistence.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface SpringDataProductRepository extends JpaRepository<ProductEntity, Long> {

    Optional<ProductEntity> findByBarcode(String barcode);

    boolean existsByName(String name);

    boolean existsByBarcode(String barcode);

    List<ProductEntity> findAllByCategory_Id(Long categoryId);        // N:1

    List<ProductEntity> findAllByPriceLessThanEqual(BigDecimal price);

    List<ProductEntity> findAllByStockLessThanEqual(Integer stock);
}
