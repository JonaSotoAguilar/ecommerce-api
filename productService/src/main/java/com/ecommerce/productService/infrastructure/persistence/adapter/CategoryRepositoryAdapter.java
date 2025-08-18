package com.ecommerce.productService.infrastructure.persistence.adapter;

import com.ecommerce.productService.application.port.out.CategoryRepositoryPort;
import com.ecommerce.productService.domain.model.Category;
import com.ecommerce.productService.infrastructure.persistence.mapper.CategoryPersistenceMapper;
import com.ecommerce.productService.infrastructure.persistence.repository.SpringDataCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CategoryRepositoryAdapter implements CategoryRepositoryPort {
    private final SpringDataCategoryRepository jpa;
    private final CategoryPersistenceMapper mapper;

    @Override
    public Category save(Category category) {
        return mapper.toDomain(jpa.save(mapper.toEntity(category)));
    }

    @Override
    public Optional<Category> findById(Long id) {
        return jpa.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Category> findAll() {
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
