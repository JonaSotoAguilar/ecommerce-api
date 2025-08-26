package com.ecommerce.productService.application.service;

import com.ecommerce.productService.application.dto.ProductDto;
import com.ecommerce.productService.application.mapper.ProductDtoMapper;
import com.ecommerce.productService.application.usecase.ProductCrudUseCase;
import com.ecommerce.productService.application.usecase.SearchProductsUseCase;
import com.ecommerce.productService.application.usecase.StockAdjustUseCase;
import com.ecommerce.productService.domain.model.Product;
import com.ecommerce.productService.domain.port.CategoryRepositoryPort;
import com.ecommerce.productService.domain.port.ProductRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService implements ProductCrudUseCase, SearchProductsUseCase, StockAdjustUseCase {

    private final ProductRepositoryPort repo;
    private final CategoryRepositoryPort categoryRepo;
    private final ApplicationEventPublisher publisher;
    private final ProductDtoMapper mapper;

    //  --- CRUD Operations ---

    @Override
    public ProductDto create(ProductDto product) {
        Product newProduct = mapper.toDomain(product);

        existsName(newProduct.getName());
        existsBarcode(newProduct.getBarcode().value());
        existsCategory(newProduct.getCategory().id());

        Product created = repo.save(newProduct);

        created.createInitialStock();
        created.pullDomainEvents().forEach(publisher::publishEvent);

        return mapper.toDto(created);
    }

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

    @Override
    @Transactional(readOnly = true)
    public ProductDto getByBarcode(String barcode) {
        return repo.findByBarcode(barcode)
                .map(mapper::toDto)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));
    }

    @Override
    public ProductDto update(ProductDto product) {
        Product current = repo.findById(product.id())
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));

        Product changes = mapper.toDomain(product);

        if (!changes.getName().equals(current.getName())) {
            existsName(changes.getName());
        }
        if (!changes.getBarcode().equals(current.getBarcode())) {
            existsBarcode(changes.getBarcode().value());
        }
        existsCategory(changes.getCategory().id());

        Product updated = repo.save(changes);

        if (!changes.getStock().equals(current.getStock())) {
            current.adjustStock(
                    changes.getStock().value() - current.getStock().value(),
                    changes.getPricing().averageCost(),
                    "Ajuste por actualización de producto"
            );
            current.pullDomainEvents().forEach(publisher::publishEvent);
        }

        return mapper.toDto(updated);
    }

    @Override
    public void delete(Long id) {
        Product product = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));

        if (product.getStock().value() > 0) {
            product.consumeOut(product.getStock().value(), "Delete Product " + product.getName());
            repo.save(product);
            product.pullDomainEvents().forEach(publisher::publishEvent);
        }

        repo.deleteById(id);
    }

    // --- Stock Management Operations ---

    @Override
    public void adjustStock(Long productId, int qty, BigDecimal unitCost, String reference) {
        Product product = repo.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));

        product.adjustStock(qty, unitCost, reference);
        repo.save(product);
        product.pullDomainEvents().forEach(publisher::publishEvent);
    }

    @Override
    public void receiveIn(Long productId, int qty, BigDecimal unitCost, String reference) {
        Product product = repo.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));

        product.receiveIn(qty, unitCost, reference);
        repo.save(product);
        product.pullDomainEvents().forEach(publisher::publishEvent);
    }

    @Override
    public void consumeOut(Long productId, int qty, String reference) {
        Product product = repo.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));

        product.consumeOut(qty, reference);
        repo.save(product);
        product.pullDomainEvents().forEach(publisher::publishEvent);
    }

    @Override
    public void devolutionIn(Long productId, int qty, String reference) {
        Product product = repo.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));

        product.devolutionIn(qty, reference);
        repo.save(product);
        product.pullDomainEvents().forEach(publisher::publishEvent);
    }

    //  --- Filters Operations ---

    @Override
    @Transactional(readOnly = true)
    public List<ProductDto> byCategory(Long categoryId) {
        categoryRepo.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Categoría no encontrada"));

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

    //  --- Validations ---

    private void existsCategory(Long categoryId) {
        if (categoryId != null) {
            categoryRepo.findById(categoryId)
                    .orElseThrow(() -> new IllegalArgumentException("Categoría no encontrado"));
        }
    }

    private void existsName(String name) {
        if (repo.existsByName(name)) {
            throw new IllegalArgumentException("Ya existe un producto con ese nombre");
        }
    }

    private void existsBarcode(String barcode) {
        if (repo.existsByBarcode(barcode)) {
            throw new IllegalArgumentException("Ya existe un producto con ese código de barras");
        }
    }

}
