package com.ecommerce.productservice.domain.model.product;

import java.util.regex.Pattern;

// model/vo/Barcode.java
public record Barcode(String value) {

    private static final Pattern PATTERN = Pattern.compile("^(\\d{8}|\\d{12}|\\d{13}|\\d{14})$");

    public Barcode {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("El código de barras no puede ser nulo o vacío");
        }
        if (!PATTERN.matcher(value).matches()) {
            throw new IllegalArgumentException("El código de barras debe tener 8, 12, 13 o 14 dígitos");
        }
    }
}