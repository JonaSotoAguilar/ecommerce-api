package com.ecommerce.productService.domain.model;

import com.ecommerce.productService.domain.event.DomainEvent;
import com.ecommerce.productService.domain.event.StockAdjustedEvent;
import com.ecommerce.productService.domain.model.constant.MovementType;
import com.ecommerce.productService.domain.model.vo.Barcode;
import com.ecommerce.productService.domain.model.vo.Pricing;
import com.ecommerce.productService.domain.model.vo.Stock;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    private Product newProduct(Long id, int initialStock, BigDecimal price, BigDecimal avgCost) {
        return new Product(
                id,
                new Barcode("12345678"),
                "  Test Product  ",
                "Desc",
                new Pricing(price, avgCost),
                new Stock(initialStock),
                Category.reference(1L)
        );
    }

    @Test
    void rename_trimsAndSetsName() {
        Product p = newProduct(1L, 0, new BigDecimal("10.00"), new BigDecimal("5.00"));
        assertEquals("Test Product", p.getName());

        p.rename("   Nuevo Nombre  ");
        assertEquals("Nuevo Nombre", p.getName());
    }

    @Test
    void rename_blankOrNull_throws() {
        Product p = newProduct(1L, 0, new BigDecimal("10.00"), new BigDecimal("5.00"));

        assertThrows(IllegalArgumentException.class, () -> p.rename("   "));
        assertThrows(IllegalArgumentException.class, () -> p.rename(null));
    }

    @Test
    void createInitialStock_whenZero_noEvent() {
        Product p = newProduct(1L, 0, new BigDecimal("10.00"), new BigDecimal("5.00"));

        p.createInitialStock();
        List<DomainEvent> events = p.pullDomainEvents();

        assertTrue(events.isEmpty());
    }

    @Test
    void createInitialStock_whenPositive_addsAdjustEvent() {
        Product p = newProduct(10L, 10, new BigDecimal("10.00"), new BigDecimal("5.00"));

        p.createInitialStock();
        List<DomainEvent> events = p.pullDomainEvents();

        assertEquals(1, events.size());
        StockAdjustedEvent e = (StockAdjustedEvent) events.get(0);
        assertEquals(MovementType.ADJUST, e.type());
        assertEquals(0, e.stockBefore());
        assertEquals(10, e.quantity());
        assertEquals(0, BigDecimal.ZERO.compareTo(e.averageCostBefore()));
        assertEquals(0, new BigDecimal("5.00").compareTo(e.unitCost()));
        assertEquals("Initial Stock", e.reference());
        assertEquals("Test Product", e.productNameSnapshot());
        assertEquals(10L, e.productId());
        assertNotNull(e.occurredAt());
    }

    @Test
    void adjustStock_updatesStockAndAverageCost_andAddsEvent() {
        Product p = newProduct(2L, 5, new BigDecimal("10.00"), new BigDecimal("4.00"));

        p.adjustStock(12, new BigDecimal("7.00"), "Recount");
        assertEquals(12, p.getStock().value());
        // updateAverageCost define escala 4
        assertEquals(0, new BigDecimal("7.0000").compareTo(p.getPricing().averageCost()));

        List<DomainEvent> events = p.pullDomainEvents();
        assertEquals(1, events.size());
        StockAdjustedEvent e = (StockAdjustedEvent) events.get(0);
        assertEquals(MovementType.ADJUST, e.type());
        assertEquals(5, e.stockBefore());
        assertEquals(12, e.quantity());
        assertEquals(0, new BigDecimal("4.00").compareTo(e.averageCostBefore()));
        assertEquals(0, new BigDecimal("7.00").compareTo(e.unitCost()));
        assertEquals("Recount", e.reference());
        assertEquals("Test Product", e.productNameSnapshot());
        assertEquals(2L, e.productId());
        assertNotNull(e.occurredAt());
    }

    @Test
    void receiveIn_increasesStock_andAddsInEvent() {
        Product p = newProduct(3L, 5, new BigDecimal("10.00"), new BigDecimal("4.00"));

        p.receiveIn(3, new BigDecimal("8.00"), "PO-1");
        assertEquals(8, p.getStock().value());

        List<DomainEvent> events = p.pullDomainEvents();
        assertEquals(1, events.size());
        StockAdjustedEvent e = (StockAdjustedEvent) events.get(0);
        assertEquals(MovementType.IN, e.type());
        assertEquals(5, e.stockBefore());
        assertEquals(3, e.quantity());
        assertEquals(0, new BigDecimal("4.00").compareTo(e.averageCostBefore()));
        assertEquals(0, new BigDecimal("8.00").compareTo(e.unitCost()));
        assertEquals("PO-1", e.reference());
        assertEquals(3L, e.productId());
    }

    @Test
    void consumeOut_decreasesStock_andAddsOutEvent() {
        Product p = newProduct(4L, 10, new BigDecimal("10.00"), new BigDecimal("2.00"));

        p.consumeOut(4, "SO-1");
        assertEquals(6, p.getStock().value());

        List<DomainEvent> events = p.pullDomainEvents();
        assertEquals(1, events.size());
        StockAdjustedEvent e = (StockAdjustedEvent) events.get(0);
        assertEquals(MovementType.OUT, e.type());
        assertEquals(10, e.stockBefore());
        assertEquals(4, e.quantity());
        // El costo promedio no cambia en OUT
        assertEquals(0, new BigDecimal("2.00").compareTo(e.averageCostBefore()));
        assertNull(e.unitCost());
        assertEquals("SO-1", e.reference());
        assertEquals(4L, e.productId());
    }

    @Test
    void consumeOut_invalidQuantity_throws() {
        Product p = newProduct(5L, 3, new BigDecimal("10.00"), new BigDecimal("2.00"));

        assertThrows(IllegalArgumentException.class, () -> p.consumeOut(0, "SO"));
        assertThrows(IllegalArgumentException.class, () -> p.consumeOut(4, "SO"));
    }

    @Test
    void devolutionIn_increasesStock_andAddsDevolutionEvent() {
        Product p = newProduct(6L, 2, new BigDecimal("10.00"), new BigDecimal("1.50"));

        p.devolutionIn(2, "RET-1");
        assertEquals(4, p.getStock().value());

        List<DomainEvent> events = p.pullDomainEvents();
        assertEquals(1, events.size());
        StockAdjustedEvent e = (StockAdjustedEvent) events.get(0);
        assertEquals(MovementType.DEVOLUTION, e.type());
        assertEquals(2, e.stockBefore());
        assertEquals(2, e.quantity());
        assertEquals(0, new BigDecimal("1.50").compareTo(e.averageCostBefore()));
        assertNull(e.unitCost());
        assertEquals("RET-1", e.reference());
    }

    @Test
    void pullDomainEvents_returnsAndClearsQueue() {
        Product p = newProduct(7L, 5, new BigDecimal("10.00"), new BigDecimal("3.00"));
        p.createInitialStock(); // genera 1 evento
        p.devolutionIn(1, "DEV-1"); // genera 1 evento

        List<DomainEvent> first = p.pullDomainEvents();
        assertEquals(2, first.size());

        List<DomainEvent> second = p.pullDomainEvents();
        assertTrue(second.isEmpty());
    }

    @Test
    void reference_factoryBuildsLightweightProduct() {
        Product ref = Product.reference(42L);
        assertEquals(42L, ref.getId());
        assertNotNull(ref.getBarcode());
        assertNotNull(ref.getPricing());
        assertNotNull(ref.getStock());
        assertNotNull(ref.getName());
    }
}
