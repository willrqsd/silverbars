package com.silverbars.orderboard;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.Iterator;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class OrderBoardTest {
    @Test
    public void testEmptyOrderBoard() {
        OrderBoard board = new OrderBoardImpl();
        assertEquals(0, board.getEntries().size());
    }

    @Test
    public void testAddSingleOrder() {
        OrderBoard board = new OrderBoardImpl();
        board.registerOrder(createOrder(12345, "4.5", "350", OrderType.BUY));
        Iterator<OrderBoardEntry> entries = board.getEntries().iterator();
        assertEquals("BUY  4.5 @ 350.00", entries.next().toString());
        assertFalse(entries.hasNext());
    }

    @Test(expected = IllegalStateException.class)
    public void testAddSameOrderTwice() {
        OrderBoard board = new OrderBoardImpl();
        board.registerOrder(createOrder(12345, "4.5", "350", OrderType.BUY));
        board.registerOrder(createOrder(12345, "4.5", "350", OrderType.BUY));
    }

    @Test
    public void testAddTwoBuyOrdersWithPricesInReverseOrder() {
        OrderBoard board = new OrderBoardImpl();
        board.registerOrder(createOrder(12345, "4.5", "350", OrderType.BUY));
        board.registerOrder(createOrder(12345, "1.5", "355", OrderType.BUY));
        Iterator<OrderBoardEntry> entries = board.getEntries().iterator();
        assertEquals("BUY  1.5 @ 355.00", entries.next().toString());
        assertEquals("BUY  4.5 @ 350.00", entries.next().toString());
        assertFalse(entries.hasNext());
    }

    @Test
    public void testAddTwoBuyOrdersWithPricesInOrder() {
        OrderBoard board = new OrderBoardImpl();
        board.registerOrder(createOrder(12345, "3.5", "360", OrderType.BUY));
        board.registerOrder(createOrder(12345, "2.5", "355", OrderType.BUY));
        Iterator<OrderBoardEntry> entries = board.getEntries().iterator();
        assertEquals("BUY  3.5 @ 360.00", entries.next().toString());
        assertEquals("BUY  2.5 @ 355.00", entries.next().toString());
        assertFalse(entries.hasNext());
    }

    @Test
    public void testAddTwoSellOrdersWithPricesInReverseOrder() {
        OrderBoard board = new OrderBoardImpl();
        board.registerOrder(createOrder(12345, "5.5", "360", OrderType.SELL));
        board.registerOrder(createOrder(12345, "1.0", "355", OrderType.SELL));
        Iterator<OrderBoardEntry> entries = board.getEntries().iterator();
        assertEquals("SELL 1.0 @ 355.00", entries.next().toString());
        assertEquals("SELL 5.5 @ 360.00", entries.next().toString());
        assertFalse(entries.hasNext());
    }

    @Test
    public void testAddTwoSellOrdersWithPricesInOrder() {
        OrderBoard board = new OrderBoardImpl();
        board.registerOrder(createOrder(12345, "2.6", "400", OrderType.SELL));
        board.registerOrder(createOrder(12345, "3.4", "401", OrderType.SELL));
        Iterator<OrderBoardEntry> entries = board.getEntries().iterator();
        assertEquals("SELL 2.6 @ 400.00", entries.next().toString());
        assertEquals("SELL 3.4 @ 401.00", entries.next().toString());
        assertFalse(entries.hasNext());
    }

    @Test
    public void testAddSimpleAggregatingOrder() {
        OrderBoard board = new OrderBoardImpl();
        board.registerOrder(createOrder(12345, "2.6", "400", OrderType.SELL));
        board.registerOrder(createOrder(12345, "1.3", "400", OrderType.SELL));
        Iterator<OrderBoardEntry> entries = board.getEntries().iterator();
        assertEquals("SELL 3.9 @ 400.00", entries.next().toString());
        assertFalse(entries.hasNext());
    }

    @Test
    public void testAddMultipleOrdersWithAggregations() {
        OrderBoard board = new OrderBoardImpl();
        board.registerOrder(createOrder(12345, "2.6", "390", OrderType.SELL));
        board.registerOrder(createOrder(12345, "2.4", "380", OrderType.BUY));
        board.registerOrder(createOrder(12345, "1.2", "375", OrderType.BUY));
        board.registerOrder(createOrder(12345, "3.5", "390", OrderType.SELL));
        board.registerOrder(createOrder(12345, "4.1", "395", OrderType.SELL));
        board.registerOrder(createOrder(12345, "0.8", "375", OrderType.BUY));
        board.registerOrder(createOrder(12345, "1.0", "370", OrderType.BUY));
        board.registerOrder(createOrder(12345, "0.5", "398", OrderType.SELL));
        board.registerOrder(createOrder(12345, "2.2", "395", OrderType.SELL));
        board.registerOrder(createOrder(12345, "0.4", "370", OrderType.BUY));
        Iterator<OrderBoardEntry> entries = board.getEntries().iterator();
        assertEquals("SELL 6.1 @ 390.00", entries.next().toString());
        assertEquals("SELL 6.3 @ 395.00", entries.next().toString());
        assertEquals("SELL 0.5 @ 398.00", entries.next().toString());
        assertEquals("BUY  2.4 @ 380.00", entries.next().toString());
        assertEquals("BUY  2.0 @ 375.00", entries.next().toString());
        assertEquals("BUY  1.4 @ 370.00", entries.next().toString());
        assertFalse(entries.hasNext());
    }

    @Test
    public void testCancelSingleOrder() {
        OrderBoard board = new OrderBoardImpl();
        board.registerOrder(createOrder(12345, "2.6", "400", OrderType.SELL));
        board.cancelOrder(createOrder(12345, "2.6", "400", OrderType.SELL));
        assertEquals(0, board.getEntries().size());
    }

    @Test(expected = IllegalStateException.class)
    public void testCancelNonExistingOrder() {
        OrderBoard board = new OrderBoardImpl();
        board.cancelOrder(createOrder(12345, "2.6", "400", OrderType.SELL));
    }

    @Test
    public void testCancelOrderTwice() {
        OrderBoard board = new OrderBoardImpl();
        board.registerOrder(createOrder(12345, "2.6", "400", OrderType.SELL));
        board.cancelOrder(createOrder(12345, "2.6", "400", OrderType.SELL));
        boolean exception = false;
        try {
            board.cancelOrder(createOrder(12345, "2.6", "400", OrderType.SELL));
        } catch (IllegalStateException ise) {
            exception = true;
        }
        assertTrue(exception);
        assertEquals(0, board.getEntries().size());
    }

    @Test
    public void testAddMultipleOrdersWithAggregationsAndCancellations() {
        OrderBoard board = new OrderBoardImpl();
        board.registerOrder(createOrder(12345, "2.6", "390", OrderType.SELL));
        board.registerOrder(createOrder(12345, "2.4", "380", OrderType.BUY));
        board.registerOrder(createOrder(12345, "1.2", "375", OrderType.BUY));
        board.registerOrder(createOrder(12345, "3.5", "390", OrderType.SELL));
        board.registerOrder(createOrder(12345, "4.1", "395", OrderType.SELL));
        board.cancelOrder(createOrder(12345, "2.6", "390", OrderType.SELL));
        board.registerOrder(createOrder(12345, "0.8", "375", OrderType.BUY));
        board.registerOrder(createOrder(12345, "1.0", "370", OrderType.BUY));
        board.cancelOrder(createOrder(12345, "2.4", "380", OrderType.BUY));
        board.registerOrder(createOrder(12345, "0.5", "398", OrderType.SELL));
        board.registerOrder(createOrder(12345, "2.2", "395", OrderType.SELL));
        board.registerOrder(createOrder(12345, "0.4", "370", OrderType.BUY));
        Iterator<OrderBoardEntry> entries = board.getEntries().iterator();
        assertEquals("SELL 3.5 @ 390.00", entries.next().toString());
        assertEquals("SELL 6.3 @ 395.00", entries.next().toString());
        assertEquals("SELL 0.5 @ 398.00", entries.next().toString());
        assertEquals("BUY  2.0 @ 375.00", entries.next().toString());
        assertEquals("BUY  1.4 @ 370.00", entries.next().toString());
        assertFalse(entries.hasNext());
    }

    @Test
    public void testFormattedSummary() {
        OrderBoard board = new OrderBoardImpl();
        board.registerOrder(createOrder(12345, "2.6", "390", OrderType.SELL));
        board.registerOrder(createOrder(12345, "2.4", "380", OrderType.BUY));
        board.registerOrder(createOrder(12345, "1.2", "375", OrderType.BUY));
        board.registerOrder(createOrder(12345, "3.5", "390", OrderType.SELL));
        board.registerOrder(createOrder(12345, "4.1", "395", OrderType.SELL));
        board.registerOrder(createOrder(12345, "0.8", "375", OrderType.BUY));
        board.registerOrder(createOrder(12345, "1.0", "370", OrderType.BUY));
        board.registerOrder(createOrder(12345, "0.5", "398", OrderType.SELL));
        board.registerOrder(createOrder(12345, "2.2", "395", OrderType.SELL));
        board.registerOrder(createOrder(12345, "0.4", "370", OrderType.BUY));
        assertEquals(
                "SELL 6.1 @ 390.00\n" +
                        "SELL 6.3 @ 395.00\n" +
                        "SELL 0.5 @ 398.00\n" +
                        "BUY  2.4 @ 380.00\n" +
                        "BUY  2.0 @ 375.00\n" +
                        "BUY  1.4 @ 370.00",
                board.getSummary()
        );
    }


    private static Order createOrder(long userId, String quantityInKg, String price, OrderType orderType) {
        return new Order(
                new UserId(userId),
                new BigDecimal(quantityInKg),
                new BigDecimal(price),
                orderType
        );
    }
}
