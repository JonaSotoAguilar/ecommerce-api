package com.ecommerce.productService.infrastructure.web.controller;

import com.ecommerce.productService.application.dto.CategoryDto;
import com.ecommerce.productService.application.usecase.CategoryCrudUseCase;
import com.ecommerce.productService.infrastructure.web.mapper.CategoryWebMapper;
import com.ecommerce.productService.infrastructure.web.request.CategoryRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryCrudUseCase crud;
    private final CategoryWebMapper mapper;

    @PostMapping
    public ResponseEntity<CategoryDto> create(@Valid @RequestBody CategoryRequest req) {
        return ResponseEntity.ok(crud.create(mapper.toDto(req)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> get(@PathVariable Long id) {
        return ResponseEntity.ok(crud.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<CategoryDto>> list() {
        return ResponseEntity.ok(crud.getAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDto> update(@PathVariable Long id, @Valid @RequestBody CategoryRequest req) {
        return ResponseEntity.ok(crud.update(mapper.toDto(id, req)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        crud.delete(id);
        return ResponseEntity.noContent().build();
    }
}
