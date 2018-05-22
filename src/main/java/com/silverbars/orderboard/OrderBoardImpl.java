package com.silverbars.orderboard;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class OrderBoardImpl implements OrderBoard {
    private final Map<PriceOrderType, OrderBoardEntry> board = new TreeMap<>();
    private final HashSet<Order> orders = new HashSet<>();

    public void registerOrder(Order order) {
        if (!orders.add(order)) throw new IllegalStateException("Order was already registered");

        // Create the Price/OrderType key for this entry
        PriceOrderType key = new PriceOrderType(order.getPricePerKg(), order.getOrderType());

        // Update the total size for this key
        OrderBoardEntry entry = board.computeIfAbsent(
                key,
                unused -> new OrderBoardEntry(order.getPricePerKg(), order.getOrderType())
        );
        entry.addQuantity(order.getQuantityInKg());
    }

    public void cancelOrder(Order order) {
        // First remove the order from the set of orders; if it cannot be found then throw an exception
        if (!orders.remove(order)) throw new IllegalStateException("Order is no longer registered");

        PriceOrderType key = new PriceOrderType(order.getPricePerKg(), order.getOrderType());
        OrderBoardEntry entry = board.get(key);
        if (entry != null) {
            entry.removeQuantity(order.getQuantityInKg());
            if (entry.getTotalQuantityInKg().signum() == 0) {
                // This entry has no quantity remaining, so remove it.
                board.remove(key);
            }
        }
    }

    public Collection<OrderBoardEntry> getEntries() {
        return board.values();
    }

    public String getSummary() {
        return board.values().stream().map(OrderBoardEntry::toString).collect(Collectors.joining("\n"));
    }
}
