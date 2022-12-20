package com.nikolai.factory;

import com.nikolai.decorator.DiscountCard;
import com.nikolai.decorator.StandardDiscountCard;

public class StandardDiscountCardFactory implements DiscountCardFactory {
    @Override
    public DiscountCard produce() {
        return new StandardDiscountCard();
    }
}
