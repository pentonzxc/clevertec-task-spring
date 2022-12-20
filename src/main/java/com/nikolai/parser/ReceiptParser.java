package com.nikolai.parser;

import com.nikolai.constants.PatternConstants;
import com.nikolai.exceptions.UnknownDiscountCardException;
import com.nikolai.exceptions.UnknownProductException;
import com.nikolai.exceptions.UnsupportedPatternException;
import com.nikolai.model.Receipt;
import com.nikolai.model.product.Product;
import com.nikolai.model.product.ProductOrder;
import com.nikolai.storage.DiscountCardStorage;
import com.nikolai.storage.ProductStorage;
import org.springframework.util.StringUtils;

public abstract class ReceiptParser implements Parser<Receipt> {
    @Override
    public Receipt parse(String text) throws UnsupportedPatternException, UnknownProductException, UnknownDiscountCardException {

        if (!StringUtils.hasText(text)) {
            throw new UnsupportedPatternException();
        }

        Receipt receipt = new Receipt();
        try {
            var line = prepareText(text);
            String[] strings = line.split(" ");

            for (String str : strings) {
                if (str.matches(PatternConstants.ORDER_PATTERN)) {
                    var data = str.split("-");
                    var product_id = Integer.parseInt(data[0]);
                    var quantity = Integer.parseInt(data[1]);
                    try {
                        Product product = ProductStorage.get(product_id);
                        receipt.add(new ProductOrder(product, quantity));
                    } catch (NullPointerException ex) {
                        throw new UnknownProductException("Product with " + product_id + " don't exist");
                    }
                } else if (str.matches(PatternConstants.DISCOUNT_CARD_PATTERN)) {
                    int card_code = Integer.parseInt(str.split("-")[1]);
                    try {
                        receipt.setDiscountCard(DiscountCardStorage.get(card_code));
                    } catch (NullPointerException ex) {
                        throw new UnknownDiscountCardException("Card with code " + card_code + " not exist");
                    }
                } else {
                    throw new UnsupportedPatternException();
                }
            }
        } catch (UnknownDiscountCardException | UnknownProductException ex) {
            throw ex;
        } catch (Exception exception) {
            throw new UnsupportedPatternException();
        }
        return receipt;
    }


    protected abstract String prepareText(String text) throws Exception;

}
