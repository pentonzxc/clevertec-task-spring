package com.nikolai.storage;

import com.nikolai.exceptions.UnknownDiscountCardException;
import com.nikolai.model.Product;

import java.util.HashMap;
import java.util.Map;

public class ProductStorage {
    public final static Map<Integer, Product> products = new HashMap<>();


    static {
        ProductStorage.add(new Product(1, 20d));
        ProductStorage.add(new Product(2, 12d));
        ProductStorage.add(new Product(3, 23d));
        ProductStorage.add(new Product(4, 7d));
        ProductStorage.add(new Product(5, 6d));
        ProductStorage.add(new Product(6, 9d));
    }


    private ProductStorage() {
    }

    private static void add(Product product) {
        products.put(product.getId(), product);
    }


    public static Product get(Integer id) {
        var product = products.get(id);
        if (product == null) {
            throw new UnknownDiscountCardException();
        }

        return product;
    }


}
