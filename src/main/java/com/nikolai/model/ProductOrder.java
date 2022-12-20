package com.nikolai.model;

public class ProductOrder {
    private final Product product;
    private int quantity;


    public ProductOrder(Product product, int quantity){
        this.product = product;
        this.quantity = quantity;
    }


    public Product getProduct() {
        return product;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }
}
