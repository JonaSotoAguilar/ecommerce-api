package com.ecommerce.productService.infrastructure.controller;

import com.ecommerce.productService.application.port.in.query.ProductQueryUseCase;
import com.ecommerce.productService.domain.model.dto.ProductDto;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/products/filter")
@RequiredArgsConstructor
public class ProductFilterController {

    private final ProductQueryUseCase useCase;

    // /api/products/filter/by-category/5
    @GetMapping("/by-category/{categoryId}")
    public ResponseEntity<List<ProductDto>> byCategory(@PathVariable Long categoryId) {
        return ResponseEntity.ok(useCase.byCategory(categoryId));
    }

    // /api/products/filter/under-price?max=1000.00
    @GetMapping("/under-price")
    public ResponseEntity<List<ProductDto>> underPrice(@RequestParam("max") BigDecimal maxPrice) {
        return ResponseEntity.ok(useCase.underPrice(maxPrice));
    }

    // /api/products/filter/under-stock?max=10
    @GetMapping("/under-stock")
    public ResponseEntity<List<ProductDto>> underStock(@RequestParam("max") @Min(0) Integer maxStock) {
        return ResponseEntity.ok(useCase.underStock(maxStock));
    }
}