package com.ecommerce.productService.domain.model;

import com.ecommerce.productService.domain.event.DomainEvent;
import com.ecommerce.productService.domain.event.StockAdjustedEvent;
import com.ecommerce.productService.domain.model.constant.MovementType;
import com.ecommerce.productService.domain.model.vo.Barcode;
import com.ecommerce.productService.domain.model.vo.Pricing;
import com.ecommerce.productService.domain.model.vo.Stock;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public final class Product {
    private final Long id;
    private final String description;
    private final Category category;
    private final Barcode barcode;
    private final List<DomainEvent> domainEvents = new ArrayList<>();
    private Pricing pricing;
    private Stock stock;
    private String name;

    public Product(Long id, Barcode barcode, String name, String description, Pricing pricing, Stock stock, Category category) {
        this.id = id;
        this.barcode = barcode;
        rename(name);
        this.description = description;
        this.pricing = pricing;
        this.stock = stock;
        this.category = category;
    }

    public static Product reference(Long id) {
        if (id == null) throw new IllegalArgumentException("id null");
        return new Product(id, new Barcode("00000000"), "null", null, new Pricing(BigDecimal.ZERO, BigDecimal.ZERO), new Stock(0), null);
    }

    public void rename(String newName) {
        if (newName == null || newName.isBlank()) {
            throw new IllegalArgumentException("Product name cannot be null or blank");
        }
        name = newName.trim();
    }

    // -- Stock Management --

    public void createInitialStock() {
        if (stock.value() == 0) {
            return;
        }

        domainEvents.add(new StockAdjustedEvent(
                MovementType.ADJUST,
                0,
                stock.value(),
                BigDecimal.ZERO,
                pricing.averageCost(),
                "Initial Stock",
                name,
                id,
                OffsetDateTime.now()
        ));
    }

    public void adjustStock(int qty, BigDecimal unitCost, String reference) {
        int oldStock = stock.value();
        BigDecimal oldUnitCost = pricing.averageCost();

        stock = new Stock(qty);
        pricing = pricing.updateAverageCost(unitCost);

        domainEvents.add(new StockAdjustedEvent(
                MovementType.ADJUST,
                oldStock,
                qty,
                oldUnitCost,
                unitCost,
                reference,
                name,
                id,
                OffsetDateTime.now()
        ));
    }

    public void receiveIn(int qty, BigDecimal unitCost, String reference) {
        int oldStock = stock.value();
        BigDecimal oldUnitCost = pricing.averageCost();

        stock = stock.add(qty);
        newAverageCost(unitCost, qty);

        domainEvents.add(new StockAdjustedEvent(
                MovementType.IN,
                oldStock,
                qty,
                oldUnitCost,
                unitCost,
                reference,
                name,
                id,
                OffsetDateTime.now()
        ));
    }

    public void consumeOut(int qty, String reference) {
        int oldStock = stock.value();

        stock = stock.subtract(qty);

        domainEvents.add(new StockAdjustedEvent(
                MovementType.OUT,
                oldStock,
                qty,
                pricing.averageCost(),
                null,
                reference,
                name,
                id,
                OffsetDateTime.now()
        ));
    }

    public void devolutionIn(int qty, String reference) {
        int oldStock = stock.value();

        stock = stock.add(qty);

        domainEvents.add(new StockAdjustedEvent(
                MovementType.DEVOLUTION,
                oldStock,
                qty,
                pricing.averageCost(),
                null,
                reference,
                name,
                id,
                OffsetDateTime.now()
        ));
    }

    private void newAverageCost(BigDecimal unitCost, int qty) {
        BigDecimal totalCost = pricing.averageCost().multiply(BigDecimal.valueOf(stock.value()));
        BigDecimal addedCost = unitCost.multiply(BigDecimal.valueOf(qty));
        int newTotalQty = stock.value() + qty;
        BigDecimal newAvgCost = totalCost.add(addedCost).divide(BigDecimal.valueOf(newTotalQty), RoundingMode.HALF_UP);
        pricing = pricing.updateAverageCost(newAvgCost);
    }

    //-- Domain Events --

    public List<DomainEvent> pullDomainEvents() {
        var out = new ArrayList<>(domainEvents);
        domainEvents.clear();
        return out;
    }

    // -- Getters --

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public Category getCategory() {
        return category;
    }

    public Barcode getBarcode() {
        return barcode;
    }

    public Pricing getPricing() {
        return pricing;
    }

    public Stock getStock() {
        return stock;
    }

    public String getName() {
        return name;
    }
}
