package com.nikolai;

import com.nikolai.model.product.Product;
import com.nikolai.dto.ProductOrder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class ProductOrderTest {


    @Mock
    private Product product;


    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void whenProductGet_thenReturnProduct() {
        Mockito.when(product.getId()).thenReturn(0);

        var productOrder = new ProductOrder(product, 0);

        Assertions.assertEquals(product, productOrder.getProduct());
    }


    @Test
    public void whenQuantityGet_thenReturnQuantity() {
        var quantity = 0;

        var productOrder = new ProductOrder(product, quantity);

        Assertions.assertEquals(quantity, productOrder.getQuantity());
    }


    @Test
    public void whenQuantityUpdate_thenReturnQuantity() {
        var productOrder = new ProductOrder(product, 0);

        productOrder.setQuantity(5);

        Assertions.assertEquals(5, productOrder.getQuantity());

    }
}
