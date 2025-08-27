package com.ecommerce.productservice.infrastructure.web.controller;

import com.ecommerce.productservice.application.dto.MovementDto;
import com.ecommerce.productservice.application.usecase.MovementCrudCase;
import com.ecommerce.productservice.application.usecase.SearchMovementUseCase;
import com.ecommerce.productservice.domain.model.movement.MovementType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/movements")
@RequiredArgsConstructor
public class MovementController {

    private final MovementCrudCase crud;
    private final SearchMovementUseCase search;

    @GetMapping("/{id}")
    public ResponseEntity<MovementDto> get(@PathVariable Long id) {
        return ResponseEntity.ok(crud.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<MovementDto>> getAll() {
        return ResponseEntity.ok(crud.getAll());
    }

    // -- Search Operations ---

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<MovementDto>> getAllByProduct(@PathVariable Long productId) {
        return ResponseEntity.ok(search.getAllByProduct(productId));
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<MovementDto>> getAllByType(@PathVariable MovementType type) {
        return ResponseEntity.ok(search.getAllByType(type));
    }

}
