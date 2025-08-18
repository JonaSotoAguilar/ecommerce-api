package com.ecommerce.productService.application.service;

import com.ecommerce.productService.application.port.in.ProductUseCase;
import com.ecommerce.productService.application.port.out.ProductRepositoryPort;
import com.ecommerce.productService.domain.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.ecommerce.productService.domain.service.ProductValidationService.validateProduct;

@Service
@RequiredArgsConstructor
public class ProductService implements ProductUseCase {

    private final ProductRepositoryPort repository;

    @Override
    public Product create(Product product) {
        validateProduct(product);
        if (repository.existsByName(product.getName())) {
            throw new IllegalArgumentException("Ya existe un producto con ese nombre");
        }
        return repository.save(product);
    }

    @Override
    public Product getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));
    }

    @Override
    public List<Product> getAll() {
        return repository.findAll();
    }

    @Override
    public Product update(Long id, Product product) {
        Product current = getById(id);
        if (StringUtils.hasText(product.getName())) current.setName(product.getName());
        current.setDescription(product.getDescription());
        if (product.getPrice() != null) current.setPrice(product.getPrice());
        if (product.getStockQuantity() != null) current.setStockQuantity(product.getStockQuantity());
        validateProduct(current);
        return repository.save(current);
    }

    @Override
    public void delete(Long id) {
        getById(id);
        repository.deleteById(id);
    }
}
