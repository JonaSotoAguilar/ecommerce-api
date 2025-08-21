package com.ecommerce.productService.application.service;

import com.ecommerce.productService.application.mapper.ProductDtoMapper;
import com.ecommerce.productService.application.port.in.command.ProductCommandUseCase;
import com.ecommerce.productService.application.port.out.CategoryRepositoryPort;
import com.ecommerce.productService.application.port.out.ProductRepositoryPort;
import com.ecommerce.productService.domain.model.Product;
import com.ecommerce.productService.domain.model.dto.ProductDto;
import com.ecommerce.productService.domain.model.dto.request.ProductRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.ecommerce.productService.domain.service.ProductValidationService.validate;

@Service
@RequiredArgsConstructor
public class ProductCommandService implements ProductCommandUseCase {

    private final ProductRepositoryPort repo;
    private final CategoryRepositoryPort categoryRepo;
    private final ProductDtoMapper mapper;

    @Override
    @Transactional
    public ProductDto create(ProductRequest request) {
        Product productDomain = mapper.toDomain(request);

        validate(productDomain);
        if (repo.existsByName(productDomain.getName())) {
            throw new IllegalArgumentException("Ya existe un producto con ese nombre");
        }

        if (productDomain.getCategory() != null)
            categoryRepo.findById(request.categoryId())
                    .orElseThrow(() -> new IllegalArgumentException("Categoría no encontrado"));

        return mapper.toDto(repo.save(productDomain));
    }

    @Override
    @Transactional
    public ProductDto update(Long id, ProductRequest request) {
        Product current = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));

        Product changes = mapper.toDomain(request);
        changes.setId(current.getId());
        validate(changes);
        if (!changes.getName().equals(current.getName()) && repo.existsByName(changes.getName())) {
            throw new IllegalArgumentException("Ya existe un producto con ese nombre");
        }
        if (changes.getCategory() != null)
            categoryRepo.findById(request.categoryId())
                    .orElseThrow(() -> new IllegalArgumentException("Categoría no encontrado"));

        return mapper.toDto(repo.save(changes));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));
        repo.deleteById(id);
    }

}
