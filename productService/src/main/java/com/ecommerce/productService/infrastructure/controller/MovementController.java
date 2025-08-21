package com.ecommerce.productService.infrastructure.controller;

import com.ecommerce.productService.application.port.in.command.MovementCommandUseCase;
import com.ecommerce.productService.application.port.in.query.MovementQueryUseCase;
import com.ecommerce.productService.domain.model.dto.MovementDto;
import com.ecommerce.productService.domain.model.dto.request.MovementRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movements")
@RequiredArgsConstructor
public class MovementController {

    private final MovementCommandUseCase command;
    private final MovementQueryUseCase query;

    @PostMapping
    public ResponseEntity<MovementDto> create(@Valid @RequestBody MovementRequest req) {
        return ResponseEntity.ok(command.create(req));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovementDto> get(@PathVariable Long id) {
        return ResponseEntity.ok(query.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<MovementDto>> getAll() {
        return ResponseEntity.ok(query.getAll());
    }

}
