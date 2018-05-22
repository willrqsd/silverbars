package com.silverbars.orderboard;

import java.math.BigDecimal;
import java.util.Objects;

// Defines a pair (price, order-type) and an ordering over that pair:
// order-type=SELL sorts before order-type=BUY
// For two Orders of the same order-type:
// - order-type=SELL sort in increasing price order
// - order-type=BUY sort in decreasing price order
public class PriceOrderType implements Comparable<PriceOrderType> {
    private final BigDecimal price;
    private final OrderType orderType;

    public PriceOrderType(BigDecimal price, OrderType orderType) {
        this.price = price;
        this.orderType = orderType;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PriceOrderType)) return false;
        PriceOrderType that = (PriceOrderType) o;
        return Objects.equals(price, that.price) &&
                orderType == that.orderType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(price, orderType);
    }

    @Override
    public int compareTo(PriceOrderType o) {
        // First order by SELL then BUY
        int c = -this.orderType.compareTo(o.orderType);
        if (c == 0) {
            // Next compare by increasing price for SELL and decreasing price for BUY
            c = this.price.compareTo(o.price);
            if (this.orderType == OrderType.BUY) c = -c;
        }
        return c;
    }
}
