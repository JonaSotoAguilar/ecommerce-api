package com.ecommerce.productservice.infrastructure.web.controller;

import com.ecommerce.productservice.application.usecase.ProductCrudUseCase;
import com.ecommerce.productservice.application.usecase.SearchProductsUseCase;
import com.ecommerce.productservice.application.usecase.StockAdjustUseCase;
import com.ecommerce.productservice.infrastructure.web.mapper.ProductWebMapper;
import com.ecommerce.productservice.infrastructure.web.request.ProductRequest;
import com.ecommerce.productservice.infrastructure.web.request.StockRequest;
import com.ecommerce.productservice.infrastructure.web.response.ProductResponse;
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
    private final ProductWebMapper mapper;

    // --- CRUD Operations ---

    @PostMapping
    public ResponseEntity<ProductResponse> create(@Valid @RequestBody ProductRequest req) {
        return ResponseEntity.ok(mapper.toResponse(crud.create(mapper.toDto(req))));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(mapper.toResponse(crud.getById(id)));
    }

    @GetMapping("/barcode/{barcode}")
    public ResponseEntity<ProductResponse> getByBarcode(@PathVariable String barcode) {
        return ResponseEntity.ok(mapper.toResponse(crud.getByBarcode(barcode)));
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAll() {
        return ResponseEntity.ok(mapper.toResponseList(crud.getAll()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> update(@PathVariable Long id, @Valid @RequestBody ProductRequest req) {
        return ResponseEntity.ok(mapper.toResponse(crud.update(mapper.toDto(id, req))));
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
    public ResponseEntity<List<ProductResponse>> byCategory(@PathVariable Long categoryId) {
        return ResponseEntity.ok(mapper.toResponseList(search.getAllByCategory(categoryId)));
    }

    @GetMapping("/under-price")
    public ResponseEntity<List<ProductResponse>> underPrice(@RequestParam("max") @Min(0) BigDecimal maxPrice) {
        return ResponseEntity.ok(mapper.toResponseList(search.getAllUnderPrice(maxPrice)));
    }

    @GetMapping("/under-stock")
    public ResponseEntity<List<ProductResponse>> underStock(@RequestParam("max") @Min(0) Integer maxStock) {
        return ResponseEntity.ok(mapper.toResponseList(search.getAllUnderStock(maxStock)));
    }
}
