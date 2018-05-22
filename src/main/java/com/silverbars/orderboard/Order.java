package com.silverbars.orderboard;

import java.math.BigDecimal;
import java.util.Objects;

public class Order {
    private final UserId userId;
    private final BigDecimal quantityInKg;
    private final BigDecimal pricePerKg;
    private final OrderType orderType;

    public Order(UserId userId, BigDecimal quantityInKg, BigDecimal pricePerKg, OrderType orderType) {
        if (userId == null) throw new NullPointerException("userId cannot be null");
        if (quantityInKg == null) throw new NullPointerException("quantityInKg cannot be null");
        if (pricePerKg == null) throw new NullPointerException("pricePerKg cannot be null");
        if (orderType == null) throw new NullPointerException("orderType cannot be null");
        if (quantityInKg.signum() <= 0) throw new IllegalArgumentException("quantityInKg must be greater than zero");
        if (pricePerKg.signum() <= 0) throw new IllegalArgumentException("pricePerKg must be greater than zero");
        this.userId = userId;
        this.quantityInKg = quantityInKg;
        this.pricePerKg = pricePerKg;
        this.orderType = orderType;
    }

    public UserId getUserId() {
        return userId;
    }

    public BigDecimal getQuantityInKg() {
        return quantityInKg;
    }

    public BigDecimal getPricePerKg() {
        return pricePerKg;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order)o;
        return Objects.equals(userId, order.userId) &&
                Objects.equals(quantityInKg, order.quantityInKg) &&
                Objects.equals(pricePerKg, order.pricePerKg) &&
                orderType == order.orderType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, quantityInKg, pricePerKg, orderType);
    }
}
