package com.ecommerce.productService.domain.model.vo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BarcodeTest {

    @Test
    void validLengths_ok() {
        assertDoesNotThrow(() -> new Barcode("12345678"));       // 8
        assertDoesNotThrow(() -> new Barcode("123456789012"));   // 12
        assertDoesNotThrow(() -> new Barcode("1234567890123"));  // 13
        assertDoesNotThrow(() -> new Barcode("12345678901234")); // 14
    }

    @Test
    void nullOrBlank_throws() {
        assertThrows(IllegalArgumentException.class, () -> new Barcode(null));
        assertThrows(IllegalArgumentException.class, () -> new Barcode("   "));
    }

    @Test
    void invalidCharactersOrLengths_throws() {
        assertThrows(IllegalArgumentException.class, () -> new Barcode("ABCDEFGH"));
        assertThrows(IllegalArgumentException.class, () -> new Barcode("1234567"));       // 7
        assertThrows(IllegalArgumentException.class, () -> new Barcode("123456789"));     // 9
        assertThrows(IllegalArgumentException.class, () -> new Barcode("123456789012345"));// 15
        assertThrows(IllegalArgumentException.class, () -> new Barcode("1234-5678"));     // no dígitos puros
    }
}
