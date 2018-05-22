package com.silverbars.orderboard;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class OrderTest {
    @Test(expected = NullPointerException.class)
    public void testCannotCreateOrderWithNullUserId() {
        new Order(null, BigDecimal.ONE, BigDecimal.ONE, OrderType.BUY);
    }

    @Test(expected = NullPointerException.class)
    public void testCannotCreateOrderWithNullQuantity() {
        new Order(new UserId(12345), null, BigDecimal.ONE, OrderType.BUY);
    }

    @Test(expected = NullPointerException.class)
    public void testCannotCreateOrderWithNullPrice() {
        new Order(new UserId(12345), BigDecimal.ONE, null, OrderType.BUY);
    }

    @Test(expected = NullPointerException.class)
    public void testCannotCreateOrderWithNullOrderType() {
        new Order(new UserId(12345), BigDecimal.ONE, BigDecimal.ONE, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCannotCreateOrderWithNegativePrice() {
        new Order(new UserId(12345), BigDecimal.ONE, new BigDecimal("-1"), OrderType.BUY);

    }

    @Test(expected = IllegalArgumentException.class)
    public void testCannotCreateOrderWithZeroPrice() {
        new Order(new UserId(12345), BigDecimal.ONE, BigDecimal.ZERO, OrderType.BUY);

    }

    @Test(expected = IllegalArgumentException.class)
    public void testCannotCreateOrderWithNegativeQuantity() {
        new Order(new UserId(12345), new BigDecimal("-1"), BigDecimal.ONE, OrderType.BUY);

    }

    @Test(expected = IllegalArgumentException.class)
    public void testCannotCreateOrderWithZeroQuantity() {
        new Order(new UserId(12345), BigDecimal.ZERO, BigDecimal.ONE, OrderType.BUY);

    }

    @Test
    public void testOrdersEqualBasedOnAllFields() {
        Order a = new Order(new UserId(12345), new BigDecimal("1234.56"), new BigDecimal("2345.67"), OrderType.BUY);
        Order b = new Order(new UserId(12345), new BigDecimal("1234.56"), new BigDecimal("2345.67"), OrderType.BUY);
        Order c = new Order(new UserId(12346), new BigDecimal("1234.56"), new BigDecimal("2345.67"), OrderType.BUY);
        Order d = new Order(new UserId(12345), new BigDecimal("1234.57"), new BigDecimal("2345.67"), OrderType.BUY);
        Order e = new Order(new UserId(12345), new BigDecimal("1234.56"), new BigDecimal("2345.68"), OrderType.BUY);
        Order f = new Order(new UserId(12345), new BigDecimal("1234.56"), new BigDecimal("2345.67"), OrderType.SELL);
        assertEquals(a, b);
        assertNotEquals(a, c);
        assertNotEquals(a, d);
        assertNotEquals(a, e);
        assertNotEquals(a, f);
    }
}
