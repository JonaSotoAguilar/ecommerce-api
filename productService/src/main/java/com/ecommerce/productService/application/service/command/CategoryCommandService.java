package com.ecommerce.productService.application.service.command;

import com.ecommerce.productService.application.mapper.CategoryDtoMapper;
import com.ecommerce.productService.application.port.in.command.CategoryCommandUseCase;
import com.ecommerce.productService.application.port.out.CategoryRepositoryPort;
import com.ecommerce.productService.domain.model.Category;
import com.ecommerce.productService.domain.model.dto.CategoryDto;
import com.ecommerce.productService.domain.model.dto.request.CategoryRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CategoryCommandService implements CategoryCommandUseCase {
    private final CategoryRepositoryPort repo;
    private final CategoryDtoMapper mapper;

    @Override
    @Transactional
    public CategoryDto create(CategoryRequest request) {
        var categoryDomain = mapper.toDomain(request);
        if (repo.existsByName(categoryDomain.getName())) {
            throw new IllegalArgumentException("La categoría ya existe");
        }
        return mapper.toDto(repo.save(categoryDomain));
    }

    @Override
    @Transactional
    public CategoryDto update(Long id, CategoryRequest request) {
        Category current = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Categoría no encontrada"));

        Category changes = mapper.toDomain(request);
        if (!changes.getName().equals(current.getName()) && repo.existsByName(changes.getName())) {
            throw new IllegalArgumentException("Ya existe una categoría con ese nombre");
        }
        changes.setId(current.getId());

        return mapper.toDto(repo.save(changes));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Categoría no encontrada"));
        repo.deleteById(id);
    }

}
