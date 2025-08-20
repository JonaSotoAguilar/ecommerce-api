package com.ecommerce.productService.application.service;

import com.ecommerce.productService.application.mapper.ProductDtoMapper;
import com.ecommerce.productService.application.port.in.ProductUseCase;
import com.ecommerce.productService.application.port.out.CategoryRepositoryPort;
import com.ecommerce.productService.application.port.out.ProductRepositoryPort;
import com.ecommerce.productService.domain.model.Product;
import com.ecommerce.productService.domain.model.dto.ProductDto;
import com.ecommerce.productService.domain.model.dto.request.ProductRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.ecommerce.productService.domain.service.ProductValidationService.validate;

@Service
@RequiredArgsConstructor
public class ProductService implements ProductUseCase {

    private final ProductRepositoryPort repo;
    private final CategoryRepositoryPort categoryRepo;
    private final ProductDtoMapper mapper;

    @Override
    public ProductDto create(ProductRequest request) {
        Product productDomain = mapper.toDomain(request);

        validate(productDomain);
        if (repo.existsByName(productDomain.getName())) {
            throw new IllegalArgumentException("Ya existe un producto con ese nombre");
        }
        //FIXME: Revisar si existe categoria

        return mapper.toDto(repo.save(productDomain));
    }

    @Override
    public ProductDto getById(Long id) {
        return repo.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));
    }

    @Override
    public List<ProductDto> getAll() {
        return mapper.toDtoList(repo.findAll());
    }

    @Override
    public ProductDto update(Long id, ProductRequest request) {
        Product current = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));

        Product changes = mapper.toDomain(request);
        if (!changes.getName().equals(current.getName()) && repo.existsByName(changes.getName())) {
            throw new IllegalArgumentException("Ya existe un producto con ese nombre");
        }
        current.setName(changes.getName());
        current.setDescription(changes.getDescription());
        current.setPrice(changes.getPrice());
        current.setStock(changes.getStock());

        //FIXME: Revisar si existe categoria
        validate(current);
        return mapper.toDto(repo.save(current));
    }

    @Override
    public void delete(Long id) {
        getById(id);
        repo.deleteById(id);
    }
}
