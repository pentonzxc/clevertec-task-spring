package com.nikolai;

import com.nikolai.model.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ProductTest {

    private Product product;
    private Product differentProduct;
    private Product sameProduct;


    @BeforeEach
    public void init() {
        product = new Product(1, 2D);
        differentProduct = new Product(2, 3D);
        sameProduct = new Product(1, 5D);
    }

    @Test
    public void whenCreateProductWithId_returnId() {
        Assertions.assertEquals(1, product.getId());
    }

    @Test
    public void whenCreateProductWithPrice_returnPrice() {
        Assertions.assertEquals(2D, product.getPrice());
    }


    @Test
    public void givenPrice_whenSetPrice_thenReturnPrice() {
        double newPrice = 3d;
        product.setPrice(newPrice);

        Assertions.assertEquals(newPrice, product.getPrice());
    }


    @Test
    public void givenProductWithAnotherId_thenEquals() {
        Assertions.assertNotEquals(product, differentProduct);
        Assertions.assertNotEquals(differentProduct, product);
    }

    @Test
    public void givenProductWithSameId_thenEquals() {
        Assertions.assertEquals(product, sameProduct);
        Assertions.assertEquals(sameProduct, product);
    }


    @Test
    public void givenProductWithAnotherId_thenHash() {
        Assertions.assertNotEquals(product.hashCode(), differentProduct.hashCode());
    }

    @Test
    public void givenProductWithSameId_thenHash() {
        Assertions.assertEquals(product.hashCode(), sameProduct.hashCode());
    }

    @Test
    public void givenEmptyProduct_thenReturnIdAndPrice() {
        Product emptyProduct = new Product();
        Assertions.assertNull(emptyProduct.getPrice());
        Assertions.assertNull(emptyProduct.getId());
    }

}
