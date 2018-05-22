package com.silverbars.orderboard;

import java.math.BigDecimal;

public class OrderBoardEntry {
    private final BigDecimal pricePerKg;
    private final OrderType orderType;
    private BigDecimal totalQuantityInKg;

    OrderBoardEntry(BigDecimal pricePerKg, OrderType orderType) {
        this.pricePerKg = pricePerKg;
        this.orderType = orderType;
        this.totalQuantityInKg = BigDecimal.ZERO;
    }

    public BigDecimal getPricePerKg() {
        return pricePerKg;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public BigDecimal getTotalQuantityInKg() {
        return totalQuantityInKg;
    }

    void addQuantity(BigDecimal quantityInKg) {
        totalQuantityInKg = totalQuantityInKg.add(quantityInKg);
    }

    void removeQuantity(BigDecimal quantityInKg) {
        totalQuantityInKg = totalQuantityInKg.subtract(quantityInKg);
    }

    @Override
    public String toString() {
        return String.format("%-4s %.1f @ %.2f", orderType, totalQuantityInKg, pricePerKg);
    }
}
