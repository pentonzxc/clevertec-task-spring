package com.nikolai.parser;

import com.nikolai.constants.PatternConstants;
import com.nikolai.exceptions.UnknownDiscountCardException;
import com.nikolai.exceptions.UnknownProductException;
import com.nikolai.exceptions.UnsupportedPatternException;
import com.nikolai.model.Receipt;
import com.nikolai.model.card.StandardDiscountCard;
import com.nikolai.model.product.Product;
import com.nikolai.model.product.ProductOrder;
import com.nikolai.service.DiscountCardService;
import com.nikolai.service.ProductService;
import org.springframework.util.StringUtils;

import java.util.Optional;

public abstract class ReceiptParser implements Parser<Receipt> {
    protected final DiscountCardService cardService;
    protected final ProductService productService;

    public ReceiptParser(DiscountCardService cardService, ProductService productService) {
        this.cardService = cardService;
        this.productService = productService;
    }


    @Override
    public Receipt parse(String text) throws UnsupportedPatternException, UnknownProductException, UnknownDiscountCardException {

        if (!StringUtils.hasText(text)) {
            throw new UnsupportedPatternException();
        }

        Receipt receipt = new Receipt();
        try {
            var line = prepareText(text);
            String[] strings = line.split(" ");

            for (int i = 0; i < strings.length; i++) {
                String str = strings[i];
                if (str.matches(PatternConstants.ORDER_PATTERN)) {
                    var data = str.split("-");
                    var product_id = Integer.parseInt(data[0]);
                    var quantity = Integer.parseInt(data[1]);
                    Optional<Product> productOpt = productService.findProductById(product_id);

                    if (productOpt.isEmpty()) {
                        throw new UnknownProductException("Product with id " + product_id + " doesn't exist");
                    }

                    receipt.add(new ProductOrder(productOpt.get(), quantity));

                } else if (i != 0 && str.matches(PatternConstants.DISCOUNT_CARD_PATTERN)) {
                    int card_code = Integer.parseInt(str.split("-")[1]);
                    Optional<StandardDiscountCard> discountCard = cardService.findCardByCode(card_code);

                    if (discountCard.isEmpty()) {
                        throw new UnknownDiscountCardException("Card with code " + card_code + " not exist");
                    }

                    receipt.setDiscountCard(discountCard.get());
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
