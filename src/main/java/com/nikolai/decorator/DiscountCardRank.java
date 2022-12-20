package com.nikolai.decorator;

public abstract class DiscountCardRank extends DiscountCard {

    public abstract String rank();

    public abstract boolean supportsUpgrade();

    public abstract Integer getDiscount();
}
