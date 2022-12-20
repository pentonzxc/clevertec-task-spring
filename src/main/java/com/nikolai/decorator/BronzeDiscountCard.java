package com.nikolai.decorator;

public class BronzeDiscountCard extends DiscountCardRank {
    private DiscountCard discountCard;

    public BronzeDiscountCard(DiscountCard discountCard) {
        this.discountCard = discountCard;
    }

    @Override
    public String rank() {
        return "Bronze " + type();
    }

    @Override
    public boolean supportsUpgrade() {
        return true;
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
