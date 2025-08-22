package com.ecommerce.productService.application.service.command;

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

        existsData(true, true, productDomain);

        return mapper.toDto(repo.save(productDomain));
    }

    @Override
    @Transactional
    public ProductDto update(Long id, ProductRequest request) {
        Product current = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));

        Product changes = mapper.toDomain(request);
        changes.setId(current.getId());
        changes.setBarcode(current.getBarcode());
        existsData(!changes.getName().equals(current.getName()), !changes.getBarcode().equals(current.getBarcode()), changes);

        return mapper.toDto(repo.save(changes));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));
        repo.deleteById(id);
    }

    private void existsData(boolean newName, boolean newBarcode, Product productDomain) {
        if (newName && repo.existsByName(productDomain.getName())) {
            throw new IllegalArgumentException("Ya existe un producto con ese nombre");
        }

        if (newBarcode && repo.existsByBarcode(productDomain.getBarcode())) {
            throw new IllegalArgumentException("Ya existe un producto con ese código de barras");
        }

        if (productDomain.getCategory() != null)
            categoryRepo.findById(productDomain.getCategory().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Categoría no encontrado"));
    }

}
