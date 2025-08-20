package com.ecommerce.productService.infrastructure.persistence.adapter;

import com.ecommerce.productService.application.port.out.ProductRepositoryPort;
import com.ecommerce.productService.domain.model.Product;
import com.ecommerce.productService.infrastructure.persistence.mapper.ProductDboMapper;
import com.ecommerce.productService.infrastructure.persistence.repository.SpringDataProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryAdapter implements ProductRepositoryPort {

    private final SpringDataProductRepository jpa;
    private final ProductDboMapper mapper;

    @Override
    public Product save(Product product) {

        var productToSave = mapper.toDbo(product);
        var productSaved = jpa.save(productToSave);

        return mapper.toDomain(productSaved);
        //return mapper.toDomain(jpa.save(mapper.toEntity(product)));
    }

    @Override
    public Optional<Product> findById(Long id) {
        return jpa.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Product> findAll() {
        return mapper.toDomainList(jpa.findAll());
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
