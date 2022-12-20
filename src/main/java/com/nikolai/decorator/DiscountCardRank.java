package com.nikolai.decorator;

import com.nikolai.model.card.DiscountCard;

public abstract class DiscountCardRank extends DiscountCard {

    public abstract String rank();

    public abstract boolean supportsUpgrade();

    public abstract Integer getDiscount();
}
