package com.ecommerce.productService.domain.model.constant;

public enum ProductStatus {
    DRAFT,        // Borrador, no visible
    ACTIVE,       // A la venta
    DISCONTINUED, // Descontinuado, no agregable al carrito
    DELETED       // Eliminado lógico
}
