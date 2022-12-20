package com.nikolai.factory;

import com.nikolai.model.card.DiscountCard;
import com.nikolai.model.card.StandardDiscountCard;
import org.springframework.stereotype.Component;


@Component("standardDiscountFactory")
public class StandardDiscountCardFactory implements DiscountCardFactory {
    @Override
    public DiscountCard produce() {
        return new StandardDiscountCard();
    }
}
