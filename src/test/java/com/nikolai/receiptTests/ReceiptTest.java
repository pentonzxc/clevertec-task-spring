package com.nikolai.receiptTests;

import com.nikolai.model.card.StandardDiscountCard;
import com.nikolai.model.card.ZeroDiscountCard;
import com.nikolai.model.product.Product;
import com.nikolai.model.Receipt;
import com.nikolai.model.product.ProductOrder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.NoSuchElementException;

public class ReceiptTest {
    private Receipt receipt;

    @BeforeEach
    public void init() {
        receipt = new Receipt();
    }


    @Test
    public void givenProductOrder_thenAddProductOrder() {
        var product = Mockito.mock(Product.class);
        Mockito.when(product.getId()).thenReturn(10);
        var expectedOrder = Mockito.mock(ProductOrder.class);

        Mockito.when(expectedOrder.getProduct()).thenReturn(product);

        receipt.add(expectedOrder);
        ProductOrder actualOrder = receipt.get(expectedOrder.getProduct().getId());

        Assertions.assertEquals(expectedOrder, actualOrder);
    }

    @Test
    public void givenProductOrders_thenAddThem_returnReceiptSize() {
        var firstProduct = Mockito.mock(Product.class);
        var secondProduct = Mockito.mock(Product.class);

        var firstOrder = Mockito.mock(ProductOrder.class);
        var secondOrder = Mockito.mock(ProductOrder.class);

        Mockito.when(firstProduct.getId()).thenReturn(10);
        Mockito.when(secondProduct.getId()).thenReturn(11);


        Mockito.when(firstOrder.getProduct()).thenReturn(firstProduct);
        Mockito.when(secondOrder.getProduct()).thenReturn(firstProduct);


        receipt.add(firstOrder);
        receipt.add(secondOrder);

        Assertions.assertEquals(1, receipt.getOrdersCount());
        Mockito.when(secondOrder.getProduct()).thenReturn(secondProduct);

        receipt.add(secondOrder);

        Assertions.assertNotEquals(1, receipt.getOrdersCount());
    }

    @Test
    public void givenProductOrdersWithSameId_thenAddThem_returnQuantity() {
        var product = Mockito.mock(Product.class);

        var order = new ProductOrder(product, 2);
        var mockedOrder = Mockito.mock(ProductOrder.class);

        Mockito.when(product.getId()).thenReturn(10);

        Mockito.when(mockedOrder.getQuantity()).thenReturn(3);

        Mockito.when(mockedOrder.getProduct()).thenReturn(product);

        var expectedQuantity = order.getQuantity() + mockedOrder.getQuantity();

        receipt.add(order);
        receipt.add(mockedOrder);
        int actualQuantity = receipt.get(product.getId()).getQuantity();

        Assertions.assertEquals(expectedQuantity, actualQuantity);
    }


    @Test
    public void whenAddProductOrder_thenGetHis() {
        var product = Mockito.mock(Product.class);
        var expected = new ProductOrder(product, 2);

        Mockito.when(product.getId()).thenReturn(1);

        receipt.add(expected);

        var actual = receipt.get(expected.getProduct().getId());

        Assertions.assertEquals(expected, actual);
    }


    @Test
    public void givenDiscountCard_whenSetCard_thenGetCard() {
        var expectedCard = Mockito.mock(StandardDiscountCard.class);
        receipt.setDiscountCard(expectedCard);

        var actualCard = receipt.getDiscountCard();

        Assertions.assertEquals(expectedCard, actualCard);
    }

    @Test

    public void givenProductOrders_whenAddAll_thenIterator() {
        var product1 = Mockito.mock(Product.class);
        var product2 = Mockito.mock(Product.class);
        var product3 = Mockito.mock(Product.class);
        var product4 = Mockito.mock(Product.class);

        Mockito.when(product1.getId()).thenReturn(10);
        Mockito.when(product2.getId()).thenReturn(11);
        Mockito.when(product3.getId()).thenReturn(12);
        Mockito.when(product4.getId()).thenReturn(12);

        var order1 = Mockito.mock(ProductOrder.class);
        var order2 = Mockito.mock(ProductOrder.class);
        var order3 = Mockito.mock(ProductOrder.class);
        var order4 = Mockito.mock(ProductOrder.class);

        Mockito.when(order1.getProduct()).thenReturn(product1);
        Mockito.when(order2.getProduct()).thenReturn(product2);
        Mockito.when(order3.getProduct()).thenReturn(product3);
        Mockito.when(order4.getProduct()).thenReturn(product4);


        receipt.add(order1);
        receipt.add(order2);
        receipt.add(order3);
        receipt.add(order4);

        var iterator = receipt.iterator();

        var iterOrder1 = iterator.next();

        var iterOrder2 = iterator.next();

        var iterOrder3 = iterator.next();

        Assertions.assertEquals(order1, iterOrder1.getValue());
        Assertions.assertEquals(order2, iterOrder2.getValue());
        Assertions.assertNotEquals(order3, iterOrder3.getValue());
        Assertions.assertNotEquals(order4, iterOrder3.getValue());

        Assertions.assertThrows(NoSuchElementException.class, () -> iterator.next(), "NoSuchElementExceptionExpected was expected");
    }


    @Test
    public void givenDiscountCard_whenSetDiscountCard_thenReturnGetDiscountCard() {
        var card = Mockito.mock(StandardDiscountCard.class);
        receipt.setDiscountCard(card);

        Assertions.assertEquals(card, receipt.getDiscountCard());
    }

    @Test
    public void givenDiscountCard_whenSetNewDiscountCard_thenReturnNewGetDiscountCard() {
        var standardCard = Mockito.mock(StandardDiscountCard.class);
        receipt.setDiscountCard(standardCard);

        var zeroCard = Mockito.mock(ZeroDiscountCard.class);

        receipt.setDiscountCard(zeroCard);

        Assertions.assertEquals(zeroCard, receipt.getDiscountCard());
    }

}
