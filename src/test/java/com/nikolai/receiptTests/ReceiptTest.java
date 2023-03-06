package com.nikolai.receiptTests;

import com.nikolai.model.Receipt;
import com.nikolai.model.card.ZeroDiscountCard;
import com.nikolai.model.product.Product;
import com.nikolai.model.product.ProductOrder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

class ReceiptTest {
    private Receipt receipt;

    @BeforeEach
    void init() {
        receipt = new Receipt();
    }


    @Test
    void whenAdd_thenGetReturnProductOrder() {
        var id = 2;
        var expectedOrder = new ProductOrder(new Product(id, 0d), 0);

        receipt.add(expectedOrder);

        Assertions.assertSame(expectedOrder, receipt.get(id));
    }

    @Test
    void whenAdd_thenGetReturnProductOrderQuantity() {
        int expectedQuantity = 2, id = 2;
        receipt.add(new ProductOrder(new Product(id, 0d), expectedQuantity));

        Assertions.assertEquals(expectedQuantity, receipt.get(id).getQuantity());
    }


    @Test
    void whenAddProductOrdersWithEqualsProducts_thenGetReturnProductOrderWithMergedQuantity() {
        int quantity1 = 2, quantity2 = 2, expectedQuantity = quantity1 + quantity2;
        var id = 1;

        receipt.add(new ProductOrder(new Product(id, 0d), quantity1));
        receipt.add(new ProductOrder(new Product(id, 0d), quantity2));

        Assertions.assertEquals(expectedQuantity, receipt.get(id).getQuantity());
    }


    @Test
    void whenAddProductOrdersWithEqualsProducts_thenOrdersCountReturnMergedOrders() {
        int expectedSize = 1, id = 1;

        receipt.add(new ProductOrder(new Product(id, 0d), 0));
        receipt.add(new ProductOrder(new Product(id, 0d), 0));

        Assertions.assertEquals(expectedSize, receipt.getOrdersCount());
    }


    @Test
    void whenAddProductOrdersWithDifferentProducts_thenGetReturnProductOrder() {
        int expectedSize = 2;
        int id1 = 1, id2 = 2;
        receipt.add(new ProductOrder(new Product(id1, 0d), 0));
        receipt.add(new ProductOrder(new Product(id2, 0d), 0));

        Assertions.assertEquals(expectedSize, receipt.getOrdersCount());
    }


    @Test
    void whenEmptyReceipt_thenGetDiscountCardReturnNull() {
        Assertions.assertNull(receipt.getDiscountCard());
    }

    @Test
    void whenSetDiscountCard_thenGetDiscountCardReturnDiscountCard() {
        var expectedCard = new ZeroDiscountCard();
        receipt.setDiscountCard(expectedCard);

        Assertions.assertSame(expectedCard, receipt.getDiscountCard());
    }

    @ParameterizedTest
    @MethodSource("com.nikolai.receiptTests.ReceiptTest#distinctProductCollection")
    void whenAddProductOrders_thenCompareIteratorNextGetProductWithOrderGetProduct(Collection<Product> products) {
        products.forEach((product) -> receipt.add(new ProductOrder(product, 0)));
        var iterator = receipt.iterator();
        for (var expectedProduct : products) {
            var product = iterator.next().getValue().getProduct();
            Assertions.assertSame(expectedProduct, product);
        }
    }


    @ParameterizedTest
    @MethodSource("com.nikolai.receiptTests.ReceiptTest#notDistinctProductCollection")
    void whenAddMergedProductOrders_thenCompareIteratorNextGetProductWithOrderGetProduct(Collection<Product> products) {
        products.forEach((product -> receipt.add(new ProductOrder(product, 0))));
        var iterator = receipt.iterator();
        for (var ignored : products) {
            if (iterator.hasNext()) {
                iterator.next();
            } else {
                Assertions.assertThrows(NoSuchElementException.class, iterator::next);
            }
        }
    }

    @Test
    void whenToString_thenReturnString() {
        var id = 1;
        var order = new ProductOrder(new Product(id, 2d), 0);
        var card = new ZeroDiscountCard();
        receipt.add(order);
        receipt.setDiscountCard(card);

        var expectedToString = "Receipt{" +
                "orders=" + Map.of(id, order) +
                ", discountCard=" + card +
                '}';

        Assertions.assertEquals(expectedToString, receipt.toString());
    }


    private static Stream<Collection<Product>> distinctProductCollection() {
        return Stream.of(
                List.of(
                        new Product(10, 0d),
                        new Product(11, 0d),
                        new Product(12, 0d)
                )
        );
    }


    private static Stream<Collection<Product>> notDistinctProductCollection() {
        return Stream.of(
                List.of(
                        new Product(10, 0d),
                        new Product(11, 0d),
                        new Product(12, 0d),
                        new Product(12, 0d)
                )
        );
    }

}
