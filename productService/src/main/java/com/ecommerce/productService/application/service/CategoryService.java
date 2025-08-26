package com.ecommerce.productService.application.service;

import com.ecommerce.productService.application.dto.CategoryDto;
import com.ecommerce.productService.application.mapper.CategoryDtoMapper;
import com.ecommerce.productService.application.usecase.CategoryCrudUseCase;
import com.ecommerce.productService.domain.model.Category;
import com.ecommerce.productService.domain.port.CategoryRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryService implements CategoryCrudUseCase {

    private final CategoryRepositoryPort repo;
    private final CategoryDtoMapper mapper;

    // --- CRUD Operations ---

    @Override
    public CategoryDto create(CategoryDto category) {
        Category categoryDomain = mapper.toDomain(category);
        existsName(categoryDomain.name());
        return mapper.toDto(repo.save(categoryDomain));
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryDto getById(Long id) {
        return repo.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new IllegalArgumentException("Categoría no encontrada"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryDto> getAll() {
        return mapper.toDtoList(repo.findAll());
    }

    @Override
    public CategoryDto update(CategoryDto category) {
        Category current = repo.findById(category.id())
                .orElseThrow(() -> new IllegalArgumentException("Categoría no encontrada"));

        Category changes = mapper.toDomain(category);
        if (!changes.name().equals(current.name())) {
            existsName(changes.name());
        }

        return mapper.toDto(repo.save(changes));
    }

    @Override
    public void delete(Long id) {
        repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Categoría no encontrada"));
        repo.deleteById(id);
    }

    // -- Validations --

    private void existsName(String name) {
        if (repo.existsByName(name)) {
            throw new IllegalArgumentException("La categoría ya existe");
        }
    }

}
