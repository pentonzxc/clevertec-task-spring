package com.nikolai.model.product;

public class ProductOrder {
    private final Product product;
    private int quantity;


    public ProductOrder(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }


    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
