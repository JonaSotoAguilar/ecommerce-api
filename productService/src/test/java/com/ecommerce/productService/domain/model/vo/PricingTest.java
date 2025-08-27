package com.ecommerce.productservice.domain.model.vo;

import com.ecommerce.productservice.domain.model.product.Pricing;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class PricingTest {

    @Test
    void constructor_nulls_throws() {
        assertThrows(IllegalArgumentException.class, () -> new Pricing(null, BigDecimal.ONE));
        assertThrows(IllegalArgumentException.class, () -> new Pricing(BigDecimal.ONE, null));
    }

    @Test
    void updateAverageCost_valid_setsScale4() {
        Pricing p = new Pricing(new BigDecimal("10.00"), new BigDecimal("2.00"));
        Pricing updated = p.updateAverageCost(new BigDecimal("3.1"));

        // precio permanece, averageCost actualizado con escala 4
        assertEquals(0, new BigDecimal("10.00").compareTo(updated.price()));
        assertEquals(0, new BigDecimal("3.1000").compareTo(updated.averageCost()));
    }

    @Test
    void updateAverageCost_nullOrNegative_throws() {
        Pricing p = new Pricing(new BigDecimal("10.00"), new BigDecimal("2.00"));
        assertThrows(IllegalArgumentException.class, () -> p.updateAverageCost(null));
        assertThrows(IllegalArgumentException.class, () -> p.updateAverageCost(new BigDecimal("-0.01")));
    }
}
