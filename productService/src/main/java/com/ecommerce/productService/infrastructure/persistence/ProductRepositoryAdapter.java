package com.ecommerce.productService.infrastructure.persistence;

import com.ecommerce.productService.application.port.out.ProductRepositoryPort;
import com.ecommerce.productService.domain.model.Product;
import com.ecommerce.productService.infrastructure.persistence.mapper.ProductPersistenceMapper;
import com.ecommerce.productService.infrastructure.persistence.repository.SpringDataProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryAdapter implements ProductRepositoryPort {

    private final SpringDataProductRepository jpa;
    private final ProductPersistenceMapper mapper;

    @Override
    public Product save(Product product) {
        return mapper.toDomain(jpa.save(mapper.toEntity(product)));
    }

    @Override
    public Optional<Product> findById(Long id) {
        return jpa.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Product> findAll() {
        return jpa.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public void deleteById(Long id) {
        jpa.deleteById(id);
    }

    @Override
    public boolean existsByName(String name) {
        return jpa.existsByName(name);
    }
}
