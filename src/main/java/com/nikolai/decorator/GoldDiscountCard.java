package com.nikolai.decorator;

import com.nikolai.model.card.DiscountCard;

public class GoldDiscountCard extends DiscountCardRank {
    private DiscountCard discountCard;

    public GoldDiscountCard(DiscountCard discountCard) {
        this.discountCard = discountCard;
    }

    @Override
    public String rank() {
        return "Gold " + type();
    }

    @Override
    public boolean supportsUpgrade() {
        return false;
    }

    @Override
    public Integer getDiscount() {
        return discountCard.getDiscount();
    }

    @Override
    public String type() {
        return discountCard.type();
    }
}
