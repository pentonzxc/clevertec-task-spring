package com.nikolai.model.card;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "discount_card", schema = "clevertec_task")
public class StandardDiscountCard extends DiscountCard {

    public StandardDiscountCard() {
        super();
    }

    public StandardDiscountCard(Integer id, int discount) {
        super(id, discount);
    }

    public StandardDiscountCard(Integer id, int discount, Integer code) {
        super(id, discount, code);
    }


    @Override
    public String type() {
        return "Standard Discount Card";
    }
}
