package com.nikolai.parser;

import com.nikolai.constants.PatternConstants;
import com.nikolai.exceptions.UnknownDiscountCardException;
import com.nikolai.exceptions.UnknownProductException;
import com.nikolai.exceptions.UnsupportedPatternException;
import com.nikolai.model.Receipt;
import com.nikolai.model.card.DiscountCard;
import com.nikolai.model.card.StandardDiscountCard;
import com.nikolai.model.product.Product;
import com.nikolai.model.product.ProductOrder;
import com.nikolai.service.DiscountCardService;
import com.nikolai.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component("webReceiptParser")
public class WebReceiptParser extends ReceiptParser {
    private final Pattern PRODUCT_PATTERN = Pattern.compile(PatternConstants.WEB_PRODUCT_PATTERN);
    private final Pattern PRODUCT_QUANTITY_PATTERN = Pattern.compile(PatternConstants.WEB_PRODUCT_QUANTITY_PATTERN);
    private final Pattern DISCOUNT_CARD_PATTERN = Pattern.compile(PatternConstants.WEB_DISCOUNT_CARD_PATTERN);


    @Autowired
    public WebReceiptParser(DiscountCardService cardService, ProductService productService) {
        super(cardService, productService);
    }


    @Override
    public Receipt parse(String text) throws RuntimeException {
        Receipt receipt = new Receipt();
        try {
            String[] strings = text.split("&");
            for (int i = 0; i < strings.length; i++) {
                if (receipt.getDiscountCard() != null) {
                    throw new UnknownProductException();
                }

                if (strings[i].matches(PatternConstants.WEB_PRODUCT_PATTERN)) {
                    Matcher matcher_id = PRODUCT_PATTERN.matcher(strings[i]);
                    matcher_id.find();

                    var product_id = Integer.parseInt(matcher_id.group(1));
                    var quantity = 1;

                    if (i + 1 != strings.length &&
                            strings[i + 1].matches(PatternConstants.WEB_PRODUCT_QUANTITY_PATTERN)) {
                        Matcher matcher_quantity = PRODUCT_QUANTITY_PATTERN.matcher(strings[i + 1]);
                        matcher_quantity.find();
                        quantity = Integer.parseInt(matcher_quantity.group(1));

                        i += 1;
                    }

                    Optional<Product> productOpt = productService.findProductById(product_id);
                    if (productOpt.isEmpty()) {
                        throw new UnknownProductException("Product with id " + product_id + " don't exist");
                    }

                    Product product = productOpt.get();

                    receipt.add(new ProductOrder(product, quantity));

                } else if (i != 0 && strings[i].matches(PatternConstants.WEB_DISCOUNT_CARD_PATTERN)) {
                    Matcher matcher_card = DISCOUNT_CARD_PATTERN.matcher(strings[i]);
                    matcher_card.find();
                    var card_code = Integer.parseInt(matcher_card.group(1));

                    Optional<StandardDiscountCard> cardOpt = cardService.findCardByCode(card_code);
                    if (cardOpt.isEmpty()) {
                        throw new UnknownDiscountCardException("Card with code " + card_code + " not exist");
                    }

                    DiscountCard card = cardOpt.get();

                    receipt.setDiscountCard(card);

                } else {
                    throw new UnknownProductException();
                }
            }

        } catch (UnknownDiscountCardException | UnknownProductException ex) {
            throw ex;
        } catch (Exception exception) {
            throw new UnsupportedPatternException();
        }
        return receipt;
    }

    @Override
    protected String prepareText(String text) throws Exception {
        throw new UnsupportedOperationException("This operation doesn't support");
    }
}
