package com.nikolai.decorator;

public abstract class DiscountCard {
    private Integer id;
    private Integer discount;
    private Integer code;

    public DiscountCard() {
        this.id = null;
        this.discount = null;
    }

    public DiscountCard(Integer id, int discount) {
        this.id = id;
        this.discount = discount;
    }

    public DiscountCard(Integer id, int discount, Integer code) {
        this.id = id;
        this.discount = discount;
        this.code = code;
    }


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public abstract String type();

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public Integer getDiscount() {
        return discount;
    }
}
