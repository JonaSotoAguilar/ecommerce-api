package com.ecommerce.productservice.domain.model.vo;

import com.ecommerce.productservice.domain.model.product.Stock;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StockTest {

    @Test
    void constructor_negative_throws() {
        assertThrows(IllegalArgumentException.class, () -> new Stock(-1));
    }

    @Test
    void add_positive_ok() {
        Stock s = new Stock(5);
        Stock s2 = s.add(3);
        assertEquals(8, s2.value());
    }

    @Test
    void add_zeroOrNegative_throws() {
        Stock s = new Stock(5);
        assertThrows(IllegalArgumentException.class, () -> s.add(0));
        assertThrows(IllegalArgumentException.class, () -> s.add(-2));
    }

    @Test
    void subtract_valid_ok() {
        Stock s = new Stock(5);
        Stock s2 = s.subtract(3);
        assertEquals(2, s2.value());
    }

    @Test
    void subtract_zeroOrGreaterThanStock_throws() {
        Stock s = new Stock(5);
        assertThrows(IllegalArgumentException.class, () -> s.subtract(0));
        assertThrows(IllegalArgumentException.class, () -> s.subtract(6));
        assertThrows(IllegalArgumentException.class, () -> s.subtract(-1));
    }
}
