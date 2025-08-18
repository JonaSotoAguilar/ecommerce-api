package com.ecommerce.productService.infrastructure.controller;

import com.ecommerce.productService.application.port.in.ProductUseCase;
import com.ecommerce.productService.domain.model.Product;
import com.ecommerce.productService.infrastructure.controller.dto.ProductRequest;
import com.ecommerce.productService.infrastructure.controller.dto.ProductResponse;
import com.ecommerce.productService.infrastructure.controller.mapper.ProductWebMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductUseCase useCase;
    private final ProductWebMapper mapper;

    @PostMapping
    public ResponseEntity<ProductResponse> create(@Valid @RequestBody ProductRequest req) {
        Product created = useCase.create(mapper.toDomain(req));
        return ResponseEntity.created(URI.create("/api/products/" + created.getId()))
                .body(mapper.toResponse(created));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> get(@PathVariable Long id) {
        return ResponseEntity.ok(mapper.toResponse(useCase.getById(id)));
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> list() {
        return ResponseEntity.ok(useCase.getAll().stream().map(mapper::toResponse).toList());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> update(@PathVariable Long id, @Valid @RequestBody ProductRequest req) {
        Product updated = useCase.update(id, mapper.toDomain(req));
        return ResponseEntity.ok(mapper.toResponse(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        useCase.delete(id);
        return ResponseEntity.noContent().build();
    }
}
