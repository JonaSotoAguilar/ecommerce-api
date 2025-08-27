package com.ecommerce.productservice.application.constant;

public enum ErrorCode {
    PRODUCT_NOT_FOUND(404, "Producto no encontrado"),
    CATEGORY_NOT_FOUND(404, "Categoría no encontrada"),
    MOVEMENT_NOT_FOUND(404, "Movimiento no encontrado"),
    PRODUCT_NAME_DUPLICATED(409, "Ya existe un producto con ese nombre"),
    PRODUCT_BARCODE_DUPLICATED(409, "Ya existe un producto con ese código de barras"),
    CATEGORY_NAME_DUPLICATED(409, "Ya existe una categoría con ese nombre"),
    VALIDATION_FAILED(400, "Validación fallida");

    public final int http;
    public final String defaultMessage;

    ErrorCode(int http, String msg) {
        this.http = http;
        this.defaultMessage = msg;
    }
}