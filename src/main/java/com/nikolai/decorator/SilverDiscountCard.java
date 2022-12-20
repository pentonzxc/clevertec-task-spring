package com.nikolai.decorator;

public class SilverDiscountCard extends DiscountCardRank {
    private DiscountCard discountCard;

    public SilverDiscountCard(DiscountCard discountCard) {
        this.discountCard = discountCard;
    }


    @Override
    public String rank() {
        return "Silver " + type();
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
