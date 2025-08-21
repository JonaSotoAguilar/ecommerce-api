package com.ecommerce.productService.application.service.query;

import com.ecommerce.productService.application.mapper.ProductDtoMapper;
import com.ecommerce.productService.application.port.in.query.ProductQueryUseCase;
import com.ecommerce.productService.application.port.out.ProductRepositoryPort;
import com.ecommerce.productService.domain.model.dto.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductQueryService implements ProductQueryUseCase {

    private final ProductRepositoryPort repo;
    private final ProductDtoMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public ProductDto getById(Long id) {
        return repo.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductDto> getAll() {
        return mapper.toDtoList(repo.findAll());
    }

    // Filters

    @Override
    @Transactional(readOnly = true)
    public List<ProductDto> byCategory(Long categoryId) {
        return mapper.toDtoList(repo.findAllByCategoryId(categoryId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductDto> underPrice(BigDecimal maxPrice) {
        return mapper.toDtoList(repo.findAllByPriceLessOrEqual(maxPrice));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductDto> underStock(Integer maxStock) {
        return mapper.toDtoList(repo.findAllByStockLessOrEqual(maxStock));
    }
}
