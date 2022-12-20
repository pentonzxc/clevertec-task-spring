package com.nikolai.factory;

import com.nikolai.decorator.DiscountCard;
import com.nikolai.decorator.ZeroDiscountCard;

public class ZeroDiscountCardFactory implements DiscountCardFactory{
    @Override
    public DiscountCard produce() {
        return new ZeroDiscountCard();
    }
}
