package com.ecommerce.productservice.domain.model.product;

import com.ecommerce.productservice.domain.event.DomainEvent;
import com.ecommerce.productservice.domain.event.StockAdjustedEvent;
import com.ecommerce.productservice.domain.model.Category;
import com.ecommerce.productservice.domain.model.movement.MovementType;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public final class Product {
    private final Long id;
    private final String description;
    private final Barcode barcode;
    private final List<DomainEvent> domainEvents = new ArrayList<>();
    private Category category;
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

        addStockAdjustedEvent(
                MovementType.ADJUST,
                0,
                stock.value(),
                BigDecimal.ZERO,
                pricing.averageCost(),
                "Initial Stock"
        );
    }

    public void adjustStock(int qty, BigDecimal unitCost, String reference) {
        int previousStock = stock.value();
        BigDecimal previousAvgCost = pricing.averageCost();

        stock = new Stock(qty);
        pricing = pricing.updateAverageCost(unitCost);

        addStockAdjustedEvent(
                MovementType.ADJUST,
                previousStock,
                qty,
                previousAvgCost,
                unitCost,
                reference
        );
    }

    public void receiveIn(int qty, BigDecimal unitCost, String reference) {
        int previousStock = stock.value();
        BigDecimal previousAvgCost = pricing.averageCost();

        stock = stock.add(qty);
        newAverageCost(unitCost, qty);

        addStockAdjustedEvent(
                MovementType.IN,
                previousStock,
                qty,
                previousAvgCost,
                unitCost,
                reference
        );
    }

    public void consumeOut(int qty, String reference) {
        int previousStock = stock.value();

        stock = stock.subtract(qty);

        addStockAdjustedEvent(
                MovementType.OUT,
                previousStock,
                qty,
                pricing.averageCost(),
                pricing.averageCost(),
                reference
        );
    }

    public void devolutionIn(int qty, String reference) {
        int previousStock
                = stock.value();

        stock = stock.add(qty);

        addStockAdjustedEvent(
                MovementType.DEVOLUTION,
                previousStock,
                qty,
                pricing.averageCost(),
                pricing.averageCost(),
                reference
        );
    }

    private void newAverageCost(BigDecimal unitCost, int qty) {
        BigDecimal totalCost = pricing.averageCost().multiply(BigDecimal.valueOf(stock.value()));
        BigDecimal addedCost = unitCost.multiply(BigDecimal.valueOf(qty));
        int newTotalQty = stock.value() + qty;
        BigDecimal newAvgCost = totalCost.add(addedCost).divide(BigDecimal.valueOf(newTotalQty), RoundingMode.HALF_UP);
        pricing = pricing.updateAverageCost(newAvgCost);
    }

    //-- Domain Events --

    private void addStockAdjustedEvent(
            MovementType type,
            int stockBefore,
            int quantity,
            BigDecimal averageCostBefore,
            BigDecimal unitCost,
            String reference
    ) {
        domainEvents.add(new StockAdjustedEvent(
                type,
                stockBefore,
                quantity,
                averageCostBefore,
                unitCost,
                reference,
                name,
                id,
                OffsetDateTime.now()
        ));
    }

    public List<DomainEvent> pullDomainEvents() {
        var out = new ArrayList<>(domainEvents);
        domainEvents.clear();
        return out;
    }

    // -- Getters & Setters --

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
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
