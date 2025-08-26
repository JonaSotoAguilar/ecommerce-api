package com.ecommerce.productService.infrastructure.web.controller;

import com.ecommerce.productService.application.dto.MovementDto;
import com.ecommerce.productService.application.usecase.MovementCrudCase;
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

    @GetMapping("/{id}")
    public ResponseEntity<MovementDto> get(@PathVariable Long id) {
        return ResponseEntity.ok(crud.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<MovementDto>> getAll() {
        return ResponseEntity.ok(crud.getAll());
    }

}
