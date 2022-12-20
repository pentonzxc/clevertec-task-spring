package com.nikolai.model.card;

public class ZeroDiscountCard extends DiscountCard {

    public ZeroDiscountCard() {
        super();
    }

    public ZeroDiscountCard(Integer id, int discount) {
        super(id, discount);
    }

    public ZeroDiscountCard(Integer id, int discount, Integer code) {
        super(id, discount, code);
    }

    @Override
    public Integer getDiscount() {
        return 0;
    }


    @Override
    public String type() {
        return "Zero Discount Card";
    }
}
