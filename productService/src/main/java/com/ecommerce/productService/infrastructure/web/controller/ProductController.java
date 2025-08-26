package com.ecommerce.productService.infrastructure.web.controller;

import com.ecommerce.productService.application.dto.ProductDto;
import com.ecommerce.productService.application.usecase.ProductCrudUseCase;
import com.ecommerce.productService.application.usecase.SearchProductsUseCase;
import com.ecommerce.productService.application.usecase.StockAdjustUseCase;
import com.ecommerce.productService.infrastructure.web.mapper.UserWebMapper;
import com.ecommerce.productService.infrastructure.web.request.ProductRequest;
import com.ecommerce.productService.infrastructure.web.request.StockRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductCrudUseCase crud;
    private final SearchProductsUseCase search;
    private final StockAdjustUseCase stock;
    private final UserWebMapper mapper;

    // --- CRUD Operations ---

    @PostMapping
    public ResponseEntity<ProductDto> create(@Valid @RequestBody ProductRequest req) {
        return ResponseEntity.ok(crud.create(mapper.toDto(req)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(crud.getById(id));
    }

    @GetMapping("/barcode/{barcode}")
    public ResponseEntity<ProductDto> getByBarcode(@PathVariable String barcode) {
        return ResponseEntity.ok(crud.getByBarcode(barcode));
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAll() {
        return ResponseEntity.ok(crud.getAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> update(@PathVariable Long id, @Valid @RequestBody ProductRequest req) {
        return ResponseEntity.ok(crud.update(mapper.toDto(id, req)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        crud.delete(id);
        return ResponseEntity.noContent().build();
    }

    // --- Stock Management Operations ---

    @PutMapping("/{id}/adjust-stock")
    public ResponseEntity<Void> adjustStock(@PathVariable Long id, @Valid @RequestBody StockRequest req) {
        stock.adjustStock(id, req.quantity(), req.unitCost(), req.reference());
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/receive-in")
    public ResponseEntity<Void> receiveIn(@PathVariable Long id, @Valid @RequestBody StockRequest req) {
        stock.receiveIn(id, req.quantity(), req.unitCost(), req.reference());
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/consume-out")
    public ResponseEntity<Void> consumeOut(@PathVariable Long id, @Valid @RequestBody StockRequest req) {
        stock.consumeOut(id, req.quantity(), req.reference());
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/devolution-in")
    public ResponseEntity<Void> devolutionIn(@PathVariable Long id, @Valid @RequestBody StockRequest req) {
        stock.devolutionIn(id, req.quantity(), req.reference());
        return ResponseEntity.noContent().build();
    }

    // --- Filters Operations ---

    @GetMapping("/by-category/{categoryId}")
    public ResponseEntity<List<ProductDto>> byCategory(@PathVariable Long categoryId) {
        return ResponseEntity.ok(search.byCategory(categoryId));
    }

    @GetMapping("/under-price")
    public ResponseEntity<List<ProductDto>> underPrice(@RequestParam("max") @Min(0) BigDecimal maxPrice) {
        return ResponseEntity.ok(search.underPrice(maxPrice));
    }

    @GetMapping("/under-stock")
    public ResponseEntity<List<ProductDto>> underStock(@RequestParam("max") @Min(0) Integer maxStock) {
        return ResponseEntity.ok(search.underStock(maxStock));
    }
}
