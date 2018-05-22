package com.silverbars.orderboard;

import java.util.Collection;

public interface OrderBoard {
    void registerOrder(Order order);
    void cancelOrder(Order order);
    Collection<OrderBoardEntry> getEntries();
    String getSummary();
}
