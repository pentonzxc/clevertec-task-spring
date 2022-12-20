package com.nikolai.factory;

import com.nikolai.model.card.DiscountCard;
import com.nikolai.model.card.ZeroDiscountCard;
import org.springframework.stereotype.Component;

@Component("zeroDiscountFactory")
public class ZeroDiscountCardFactory implements DiscountCardFactory {
    @Override
    public DiscountCard produce() {
        return new ZeroDiscountCard();
    }
}
