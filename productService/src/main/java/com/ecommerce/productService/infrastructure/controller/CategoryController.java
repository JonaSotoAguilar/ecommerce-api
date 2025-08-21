package com.ecommerce.productService.infrastructure.controller;

import com.ecommerce.productService.application.port.in.command.CategoryCommandUseCase;
import com.ecommerce.productService.application.port.in.query.CategoryQueryUseCase;
import com.ecommerce.productService.domain.model.dto.CategoryDto;
import com.ecommerce.productService.domain.model.dto.request.CategoryRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryCommandUseCase command;
    private final CategoryQueryUseCase query;

    @PostMapping
    public ResponseEntity<CategoryDto> create(@Valid @RequestBody CategoryRequest req) {
        return ResponseEntity.ok(command.create(req));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> get(@PathVariable Long id) {
        return ResponseEntity.ok(query.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<CategoryDto>> list() {
        return ResponseEntity.ok(query.getAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDto> update(@PathVariable Long id, @Valid @RequestBody CategoryRequest req) {
        return ResponseEntity.ok(command.update(id, req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        command.delete(id);
        return ResponseEntity.noContent().build();
    }
}
