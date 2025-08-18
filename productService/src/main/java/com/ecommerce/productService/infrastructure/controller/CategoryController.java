package com.ecommerce.productService.infrastructure.controller;

import com.ecommerce.productService.application.port.in.CategoryUseCase;
import com.ecommerce.productService.domain.model.Category;
import com.ecommerce.productService.infrastructure.controller.dto.CategoryRequest;
import com.ecommerce.productService.infrastructure.controller.dto.CategoryResponse;
import com.ecommerce.productService.infrastructure.controller.mapper.CategoryWebMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryUseCase useCase;
    private final CategoryWebMapper mapper;

    @PostMapping
    public ResponseEntity<CategoryResponse> create(@Valid @RequestBody CategoryRequest req) {
        Category created = useCase.create(mapper.toDomain(req));
        return ResponseEntity.created(URI.create("/api/categories/" + created.getId()))
                .body(mapper.toResponse(created));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> get(@PathVariable Long id) {
        return ResponseEntity.ok(mapper.toResponse(useCase.getById(id)));
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> list() {
        return ResponseEntity.ok(useCase.getAll().stream().map(mapper::toResponse).toList());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> update(@PathVariable Long id, @Valid @RequestBody CategoryRequest req) {
        Category updated = useCase.update(id, mapper.toDomain(req));
        return ResponseEntity.ok(mapper.toResponse(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        useCase.delete(id);
        return ResponseEntity.noContent().build();
    }
}
