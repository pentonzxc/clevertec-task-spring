package com.nikolai.productTests;

import com.nikolai.model.product.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ProductTest {

    private Product product;


    @AfterEach
    void cleanUp() {
        product = null;
    }


    @Test
    void whenGetId_returnId() {
        int expectedId = 1;
        product = new Product(expectedId, 1d);

        Assertions.assertEquals(expectedId, product.getId());
    }

    @Test
    void whenProductEmpty_thenGetIdReturnNull() {
        product = new Product();
        Assertions.assertNull(product.getId());
    }

    @Test
    void whenSetId_thenGetIdReturnId() {
        int expectedId = 1;
        product = new Product();
        product.setId(expectedId);

        Assertions.assertEquals(expectedId, product.getId());
    }

    @Test
    void whenGetPrice_thenReturnPrice() {
        double expectedPrice = 2d;
        product = new Product(expectedPrice);

        Assertions.assertEquals(expectedPrice, product.getPrice());
    }

    @Test
    void whenProductEmpty_thenGetPriceReturnNull() {
        product = new Product();
        Assertions.assertNull(product.getPrice());
    }


    @Test
    void whenSetPrice_thenGetPriceReturnPrice() {
        double expectedPrice = 3d;
        product = new Product();
        product.setPrice(expectedPrice);

        Assertions.assertEquals(expectedPrice, product.getPrice());
    }


    @Test
    void whenProductsWithDifferentId_thenEqualsReturnFalse() {
        product = new Product(1, 2d);
        var anotherProduct = new Product(2, 2d);

        Assertions.assertNotEquals(product, anotherProduct);
    }

    @Test
    void whenProductsWithSameId_thenEqualsReturnTrue() {
        product = new Product(1, 2d);
        var sameProduct = new Product(1, 2d);

        Assertions.assertEquals(product, sameProduct);
    }


    @Test
    void whenProductsWithDifferentId_thenHashCodesReturnDifferentValues() {
        product = new Product(1, 2d);
        var anotherProduct = new Product(2, 2d);

        Assertions.assertNotEquals(product.hashCode(), anotherProduct.hashCode());
    }

    @Test
    void whenProductsWithSameId_thenHashCodesReturnSameValues() {
        product = new Product(1, 2d);
        var sameProduct = new Product(1, 2d);

        Assertions.assertEquals(product.hashCode(), sameProduct.hashCode());
    }


    @Test
    void whenToString_thenReturnString() {
        product = new Product();
        var expectedToString = "Product{" +
                "id=" + product.getId() +
                ", cost=" + product.getPrice() +
                '}';

        Assertions.assertEquals(expectedToString, product.toString());
    }
}
