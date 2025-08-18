package com.ecommerce.productService.application.service;

import com.ecommerce.productService.application.port.in.CategoryUseCase;
import com.ecommerce.productService.application.port.out.CategoryRepositoryPort;
import com.ecommerce.productService.domain.model.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.ecommerce.productService.domain.service.CategoryValidationService.validate;

@Service
@RequiredArgsConstructor
public class CategoryService implements CategoryUseCase {
    private final CategoryRepositoryPort repo;

    @Override
    public Category create(Category c) {
        validate(c);
        if (repo.existsByName(c.getName())) {
            throw new IllegalArgumentException("La categoría ya existe");
        }
        return repo.save(c);
    }

    @Override
    public Category getById(Long id) {
        return repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Categoría no encontrada"));
    }

    @Override
    public List<Category> getAll() {
        return repo.findAll();
    }

    @Override
    public Category update(Long id, Category c) {
        Category current = getById(id);
        if (StringUtils.hasText(c.getName())) current = Category.builder()
                .id(current.getId())
                .name(c.getName())
                .description(c.getDescription())
                .build();
        else current = Category.builder()
                .id(current.getId())
                .name(current.getName())
                .description(c.getDescription())
                .build();
        validate(current);
        return repo.save(current);
    }

    @Override
    public void delete(Long id) {
        getById(id); // asegura existencia
        repo.deleteById(id);
    }

}
