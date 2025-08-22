package com.ecommerce.productService.infrastructure.controller;

import com.ecommerce.productService.application.port.in.command.ProductCommandUseCase;
import com.ecommerce.productService.application.port.in.query.ProductQueryUseCase;
import com.ecommerce.productService.domain.model.dto.ProductDto;
import com.ecommerce.productService.domain.model.dto.request.ProductRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductCommandUseCase command;
    private final ProductQueryUseCase query;

    @PostMapping
    public ResponseEntity<ProductDto> create(@Valid @RequestBody ProductRequest req) {
        return ResponseEntity.ok(command.create(req));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(query.getById(id));
    }

    @GetMapping("/barcode")
    public ResponseEntity<ProductDto> getByBarcode(@RequestParam String barcode) {
        return ResponseEntity.ok(query.getByBarcode(barcode));
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAll() {
        return ResponseEntity.ok(query.getAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateById(@PathVariable Long id, @Valid @RequestBody ProductRequest req) {
        return ResponseEntity.ok(command.update(id, req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        command.delete(id);
        return ResponseEntity.noContent().build();
    }
}
