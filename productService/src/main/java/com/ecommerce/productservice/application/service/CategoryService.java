package com.ecommerce.productservice.application.service;

import com.ecommerce.productservice.application.dto.CategoryDto;
import com.ecommerce.productservice.application.exception.BusinessException;
import com.ecommerce.productservice.application.exception.ErrorCode;
import com.ecommerce.productservice.application.mapper.CategoryDtoMapper;
import com.ecommerce.productservice.application.usecase.CategoryCrudUseCase;
import com.ecommerce.productservice.domain.model.Category;
import com.ecommerce.productservice.domain.port.CategoryRepositoryPort;
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
        return mapper.toDto(getCategoryOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryDto> getAll() {
        return mapper.toDtoList(repo.findAll());
    }

    @Override
    public CategoryDto update(CategoryDto category) {
        Category current = getCategoryOrThrow(category.id());
        Category changes = mapper.toDomain(category);

        if (!changes.name().equals(current.name())) {
            existsName(changes.name());
        }

        return mapper.toDto(repo.save(changes));
    }

    @Override
    public void delete(Long id) {
        getCategoryOrThrow(id);
        repo.deleteById(id);
    }

    // -- Validations & Helpers --

    private Category getCategoryOrThrow(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.CATEGORY_NOT_FOUND));
    }

    private void existsName(String name) {
        if (repo.existsByName(name)) {
            throw new BusinessException(ErrorCode.CATEGORY_NAME_DUPLICATED);
        }
    }

}
