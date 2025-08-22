package com.ecommerce.productService.application.service.query;

import com.ecommerce.productService.application.mapper.CategoryDtoMapper;
import com.ecommerce.productService.application.port.in.query.CategoryQueryUseCase;
import com.ecommerce.productService.application.port.out.CategoryRepositoryPort;
import com.ecommerce.productService.domain.model.dto.CategoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryQueryService implements CategoryQueryUseCase {

    private final CategoryRepositoryPort repo;
    private final CategoryDtoMapper mapper;

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
}
