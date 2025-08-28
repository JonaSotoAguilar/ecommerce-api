package com.ecommerce.productservice.application.service;

import com.ecommerce.productservice.application.dto.ProductDto;
import com.ecommerce.productservice.application.exception.BusinessException;
import com.ecommerce.productservice.application.exception.ErrorCode;
import com.ecommerce.productservice.application.mapper.ProductDtoMapper;
import com.ecommerce.productservice.application.usecase.ProductCrudUseCase;
import com.ecommerce.productservice.application.usecase.SearchProductsUseCase;
import com.ecommerce.productservice.application.usecase.StockAdjustUseCase;
import com.ecommerce.productservice.domain.model.Category;
import com.ecommerce.productservice.domain.model.product.Product;
import com.ecommerce.productservice.domain.port.CategoryRepositoryPort;
import com.ecommerce.productservice.domain.port.ProductRepositoryPort;
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

    private final ProductRepositoryPort repo;
    private final CategoryRepositoryPort categoryRepo;
    private final ApplicationEventPublisher publisher;
    private final ProductDtoMapper mapper;

    //  --- CRUD Operations ---

    @Override
    public ProductDto create(ProductDto product) {
        Product newProduct = mapper.toDomain(product);

        validateUniquenessOnCreate(newProduct);
        if (newProduct.getCategory() != null) {
            newProduct.setCategory(getCategoryOrThrow(newProduct.getCategory().id()));
        }

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
                .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_FOUND));
    }

    @Override
    public ProductDto update(ProductDto product) {
        Product current = getProductOrThrow(product.id());
        Product changes = mapper.toDomain(product);

        validateUniquenessOnUpdate(current, changes);
        if (changes.getCategory() != null) {
            changes.setCategory(getCategoryOrThrow(changes.getCategory().id()));
        }
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
    public List<ProductDto> getAllByCategory(Long categoryId) {
        getCategoryOrThrow(categoryId);

        return mapper.toDtoList(repo.findAllByCategoryId(categoryId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductDto> getAllUnderPrice(BigDecimal maxPrice) {
        return mapper.toDtoList(repo.findAllByPriceLessOrEqual(maxPrice));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductDto> getAllUnderStock(Integer maxStock) {
        return mapper.toDtoList(repo.findAllByStockLessOrEqual(maxStock));
    }

    //  --- Validations & Helpers ---

    private void assertNameAvailable(String name) {
        if (repo.existsByName(name)) {
            throw new BusinessException(ErrorCode.PRODUCT_NAME_DUPLICATED);
        }
    }

    private void assertBarcodeAvailable(String barcode) {
        if (repo.existsByBarcode(barcode)) {
            throw new BusinessException(ErrorCode.PRODUCT_BARCODE_DUPLICATED);
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

    private Category getCategoryOrThrow(Long categoryId) {
        return categoryRepo.findById(categoryId)
                .orElseThrow(() -> new BusinessException(ErrorCode.CATEGORY_NOT_FOUND));
    }

    private Product getProductOrThrow(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_FOUND));
    }

    private void withProductMutated(Long productId, Consumer<Product> mutator) {
        Product product = getProductOrThrow(productId);
        mutator.accept(product);
        repo.save(product);
        product.pullDomainEvents().forEach(publisher::publishEvent);
    }
}