package com.nikolai.productTests;

import com.nikolai.model.product.Product;
import com.nikolai.model.product.ProductOrder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ProductOrderTest {
    Product product;

    ProductOrder productOrder;

    @BeforeEach
    public void init() {
        product = new Product(0, 0d);
        productOrder = new ProductOrder(product , 0);
    }

    @Test
    public void whenGetProduct_thenReturnProduct() {
        Assertions.assertEquals(product, productOrder.getProduct());
    }


    @Test
    public void whenGetQuantity_thenReturnQuantity() {
        var expectedQuantity = 1;
        productOrder = new ProductOrder(product, expectedQuantity);

        Assertions.assertEquals(expectedQuantity, productOrder.getQuantity());
    }


    @Test
    public void whenQuantityUpdate_thenReturnUpdatedQuantity() {
        productOrder.setQuantity(5);
        Assertions.assertEquals(5, productOrder.getQuantity());
    }
}
