package com.nikolai.storage;

import com.nikolai.decorator.DiscountCard;
import com.nikolai.decorator.StandardDiscountCard;
import com.nikolai.exceptions.UnknownDiscountCardException;

import java.util.HashMap;
import java.util.Map;

public class DiscountCardStorage {
    public final static Map<Integer, DiscountCard> cards = new HashMap<>();

    private DiscountCardStorage() {
    }

    static {
        DiscountCardStorage.add(4475, new StandardDiscountCard(1, 6));
        DiscountCardStorage.add(1925, new StandardDiscountCard(2, 15));
        DiscountCardStorage.add(5682, new StandardDiscountCard(3, 50));
    }

    public static void add(int identityCode, DiscountCard card) {
        cards.put(identityCode, card);
    }


    public static DiscountCard get(Integer id) throws UnknownDiscountCardException {
        var discountCard = cards.get(id);
        if (discountCard == null) {
            throw new UnknownDiscountCardException();
        }

        return discountCard;
    }

}
