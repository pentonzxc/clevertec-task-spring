package com.nikolai.model;

import java.util.Objects;

public class Product {
    private Integer id;
    private Double price;

    public Product() {
        this.id = null;
        this.price = null;
    }

    public Product(Double price) {
        this.price = price;
    }

    public Product(Integer id, Double price) {
        this.id = id;
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id.equals(product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", cost=" + price +
                '}';
    }
}
