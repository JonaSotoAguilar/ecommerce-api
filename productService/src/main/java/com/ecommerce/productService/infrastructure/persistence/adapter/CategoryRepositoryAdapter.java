package com.ecommerce.productservice.infrastructure.persistence.adapter;

import com.ecommerce.productservice.domain.model.Category;
import com.ecommerce.productservice.domain.port.CategoryRepositoryPort;
import com.ecommerce.productservice.infrastructure.persistence.mapper.CategoryDboMapper;
import com.ecommerce.productservice.infrastructure.persistence.repository.SpringDataCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CategoryRepositoryAdapter implements CategoryRepositoryPort {
    private final SpringDataCategoryRepository db;
    private final CategoryDboMapper mapper;

    @Override
    public Category save(Category category) {
        return mapper.toDomain(db.save(mapper.toEntity(category)));
    }

    @Override
    public Optional<Category> findById(Long id) {
        return db.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Category> findAll() {
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
}
