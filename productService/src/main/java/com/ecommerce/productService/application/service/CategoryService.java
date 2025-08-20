package com.ecommerce.productService.application.service;

import com.ecommerce.productService.application.mapper.CategoryDtoMapper;
import com.ecommerce.productService.application.port.in.CategoryUseCase;
import com.ecommerce.productService.application.port.out.CategoryRepositoryPort;
import com.ecommerce.productService.domain.model.Category;
import com.ecommerce.productService.domain.model.dto.CategoryDto;
import com.ecommerce.productService.domain.model.dto.request.CategoryRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.ecommerce.productService.domain.service.CategoryValidationService.validate;

@Service
@RequiredArgsConstructor
public class CategoryService implements CategoryUseCase {
    private final CategoryRepositoryPort repo;
    private final CategoryDtoMapper mapper;

    @Override
    public CategoryDto create(CategoryRequest request) {
        var categoryDomain = mapper.toDomain(request);
        validate(categoryDomain);
        if (repo.existsByName(categoryDomain.getName())) {
            throw new IllegalArgumentException("La categoría ya existe");
        }
        return mapper.toDto(repo.save(categoryDomain));
    }

    @Override
    public CategoryDto getById(Long id) {
        return repo.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new IllegalArgumentException("Categoría no encontrada"));
    }

    @Override
    public List<CategoryDto> getAll() {
        return mapper.toDtoList(repo.findAll());
    }

    @Override
    public CategoryDto update(Long id, CategoryRequest request) {
        Category current = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Categoría no encontrada"));

        Category changes = mapper.toDomain(request);
        if (!changes.getName().equals(current.getName()) && repo.existsByName(changes.getName())) {
            throw new IllegalArgumentException("Ya existe un producto con ese nombre");
        }
        current.setName(changes.getName());
        current.setDescription(changes.getDescription());

        validate(current);
        return mapper.toDto(repo.save(current));
    }

    @Override
    public void delete(Long id) {
        getById(id); // asegura existencia
        repo.deleteById(id);
    }
    

}
