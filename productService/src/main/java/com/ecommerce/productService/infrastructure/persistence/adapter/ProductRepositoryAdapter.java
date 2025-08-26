package com.ecommerce.productService.infrastructure.persistence.adapter;

import com.ecommerce.productService.domain.model.Product;
import com.ecommerce.productService.domain.port.ProductRepositoryPort;
import com.ecommerce.productService.infrastructure.persistence.mapper.ProductDboMapper;
import com.ecommerce.productService.infrastructure.persistence.repository.SpringDataProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryAdapter implements ProductRepositoryPort {

    private final SpringDataProductRepository db;
    private final ProductDboMapper mapper;

    @Override
    public Product save(Product product) {
        return mapper.toDomain(db.save(mapper.toDbo(product)));
    }

    @Override
    public Optional<Product> findById(Long id) {
        return db.findById(id).map(mapper::toDomain);
    }

    @Override
    public Optional<Product> findByBarcode(String barcode) {
        return db.findByBarcode(barcode).map(mapper::toDomain);
    }

    @Override
    public List<Product> findAll() {
        return mapper.toDomainList(db.findAll());
    }

    @Override
    public void deleteById(Long id) {
        db.deleteById(id);
    }

    @Override
    public boolean existsByName(String name) {
        return db.existsByName(name);
    }

    @Override
    public boolean existsByBarcode(String barcode) {
        return db.existsByBarcode(barcode);
    }

    // --- Filtros ---
    @Override
    public List<Product> findAllByCategoryId(Long categoryId) {
        return mapper.toDomainList(db.findAllByCategory_Id(categoryId));
    }

    @Override
    public List<Product> findAllByPriceLessOrEqual(BigDecimal maxPrice) {
        return mapper.toDomainList(db.findAllByPriceLessThanEqual(maxPrice));
    }

    @Override
    public List<Product> findAllByStockLessOrEqual(Integer maxStock) {
        return mapper.toDomainList(db.findAllByStockLessThanEqual(maxStock));
    }
}
