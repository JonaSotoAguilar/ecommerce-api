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
import java.util.function.Consumer;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService implements ProductCrudUseCase, SearchProductsUseCase, StockAdjustUseCase {

    private static final String ERR_PRODUCTO_NO_ENCONTRADO = "Producto no encontrado";
    private static final String ERR_CATEGORIA_NO_ENCONTRADA = "Categoría no encontrada";
    private static final String ERR_PRODUCTO_NOMBRE_DUPLICADO = "Ya existe un producto con ese nombre";
    private static final String ERR_PRODUCTO_BARCODE_DUPLICADO = "Ya existe un producto con ese código de barras";

    private final ProductRepositoryPort repo;
    private final CategoryRepositoryPort categoryRepo;
    private final ApplicationEventPublisher publisher;
    private final ProductDtoMapper mapper;

    //  --- CRUD Operations ---

    @Override
    public ProductDto create(ProductDto product) {
        Product newProduct = mapper.toDomain(product);
        validateUniquenessOnCreate(newProduct);
        assertCategoryExists(newProduct.getCategory().id());

        Product created = repo.save(newProduct);
        created.createInitialStock();
        created.pullDomainEvents().forEach(publisher::publishEvent);

        return mapper.toDto(created);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductDto getById(Long id) {
        return mapper.toDto(getProductOrThrow(id));
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
                .orElseThrow(() -> new IllegalArgumentException(ERR_PRODUCTO_NO_ENCONTRADO));
    }

    @Override
    public ProductDto update(ProductDto product) {
        Product current = getProductOrThrow(product.id());
        Product changes = mapper.toDomain(product);

        validateUniquenessOnUpdate(current, changes);
        assertCategoryExists(changes.getCategory().id());

        if (!changes.getStock().equals(current.getStock())) {
            withProductMutated(current.getId(), p -> p.adjustStock(
                    changes.getStock().value(),
                    changes.getPricing().averageCost(),
                    "Ajuste por actualización de producto"
            ));
        }

        return mapper.toDto(repo.save(changes));
    }

    @Override
    public void delete(Long id) {
        Product product = getProductOrThrow(id);

        if (product.getStock().value() > 0) {
            withProductMutated(id, p -> p.consumeOut(p.getStock().value(), "Delete Product " + p.getName()));
        }

        repo.deleteById(id);
    }

    // --- Stock Management Operations ---

    @Override
    public void adjustStock(Long productId, int qty, BigDecimal unitCost, String reference) {
        withProductMutated(productId, p -> p.adjustStock(qty, unitCost, reference));
    }

    @Override
    public void receiveIn(Long productId, int qty, BigDecimal unitCost, String reference) {
        withProductMutated(productId, p -> p.receiveIn(qty, unitCost, reference));
    }

    @Override
    public void consumeOut(Long productId, int qty, String reference) {
        withProductMutated(productId, p -> p.consumeOut(qty, reference));
    }

    @Override
    public void devolutionIn(Long productId, int qty, String reference) {
        withProductMutated(productId, p -> p.devolutionIn(qty, reference));
    }

    //  --- Filters Operations ---

    @Override
    @Transactional(readOnly = true)
    public List<ProductDto> byCategory(Long categoryId) {
        assertCategoryExists(categoryId);

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

    //  --- Validations & Helpers ---

    private void assertCategoryExists(Long categoryId) {
        if (categoryId != null) {
            categoryRepo.findById(categoryId)
                    .orElseThrow(() -> new IllegalArgumentException(ERR_CATEGORIA_NO_ENCONTRADA));
        }
    }

    private void assertNameAvailable(String name) {
        if (repo.existsByName(name)) {
            throw new IllegalArgumentException(ERR_PRODUCTO_NOMBRE_DUPLICADO);
        }
    }

    private void assertBarcodeAvailable(String barcode) {
        if (repo.existsByBarcode(barcode)) {
            throw new IllegalArgumentException(ERR_PRODUCTO_BARCODE_DUPLICADO);
        }
    }

    private void validateUniquenessOnCreate(Product product) {
        assertNameAvailable(product.getName());
        assertBarcodeAvailable(product.getBarcode().value());
    }

    private void validateUniquenessOnUpdate(Product current, Product changes) {
        if (!changes.getName().equals(current.getName())) {
            assertNameAvailable(changes.getName());
        }
        if (!changes.getBarcode().equals(current.getBarcode())) {
            assertBarcodeAvailable(changes.getBarcode().value());
        }
    }

    private Product getProductOrThrow(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(ERR_PRODUCTO_NO_ENCONTRADO));
    }

    private void withProductMutated(Long productId, Consumer<Product> mutator) {
        Product product = getProductOrThrow(productId);
        mutator.accept(product);
        repo.save(product);
        product.pullDomainEvents().forEach(publisher::publishEvent);
    }
}