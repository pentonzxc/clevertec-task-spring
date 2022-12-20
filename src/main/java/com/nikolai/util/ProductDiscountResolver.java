package com.nikolai.util;

import com.nikolai.constants.CardRankConstants;
import com.nikolai.constants.ProductConstants;
import com.nikolai.decorator.BronzeDiscountCard;
import com.nikolai.decorator.DiscountCard;
import com.nikolai.decorator.GoldDiscountCard;
import com.nikolai.decorator.SilverDiscountCard;
import com.nikolai.model.ProductOrder;

public class ProductDiscountResolver {
    private ProductDiscountResolver() {
    }

    public static int resolve(ProductOrder order, DiscountCard discountCard) {
        var discount = discountCard.getDiscount();
        if (order.getQuantity() >= 5) {
            discount += ProductConstants.WITH_QUANTITY_DISCOUNT;

            if (discountCard instanceof BronzeDiscountCard) {
                discount += CardRankConstants.BRONZE_QUANTITY_DISCOUNT;
            } else if (discountCard instanceof SilverDiscountCard) {
                discount += CardRankConstants.SILVER_QUANTITY_DISCOUNT;
            } else if (discountCard instanceof GoldDiscountCard) {
                discount += CardRankConstants.GOLD_QUANTITY_DISCOUNT;
            }
        }
        return discount;


//        int discount = ProductConstants.WITH_QUANTITY_DISCOUNT;
//        if (discountCard instanceof BronzeDiscountCard) {
//            discount += CardRankConstants.BRONZE_QUANTITY_DISCOUNT;
//        } else if (discountCard instanceof SilverDiscountCard) {
//            discount += CardRankConstants.SILVER_QUANTITY_DISCOUNT;
//        } else if (discountCard instanceof GoldDiscountCard) {
//            discount += CardRankConstants.GOLD_QUANTITY_DISCOUNT;
//        }
//        return discount;
    }
}
