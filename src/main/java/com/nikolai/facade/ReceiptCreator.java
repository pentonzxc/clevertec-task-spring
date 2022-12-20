package com.nikolai.facade;

import com.nikolai.decorator.DiscountCard;
import com.nikolai.factory.DiscountCardFactory;
import com.nikolai.factory.ZeroDiscountCardFactory;
import com.nikolai.model.Receipt;
import com.nikolai.parser.CommandLineReceiptParser;
import com.nikolai.parser.FileReceiptParser;
import com.nikolai.parser.ParserProvider;
import com.nikolai.parser.ReceiptParserProvider;
import com.nikolai.service.ReceiptService;
import com.nikolai.util.ReceiptFormatter;

import java.util.List;

public class ReceiptCreator {
    public void createReceipt(String text) {
        ParserProvider<Receipt> provider = new ReceiptParserProvider();
        provider.register(
                List.of(
                        new FileReceiptParser(),
                        new CommandLineReceiptParser()
                )
        );

        ReceiptFormatter formatter = null;

        Receipt receipt = provider.parse(text);

        DiscountCardFactory cardFactory = null;
        DiscountCard cardOpt = receipt.getDiscountCard();

        if (cardOpt == null) {
            cardFactory = new ZeroDiscountCardFactory();
            DiscountCard discountCard = cardFactory.produce();
            receipt.setDiscountCard(discountCard);
        }

        ReceiptService receiptService = new ReceiptService(receipt);
        formatter = new ReceiptFormatter(receiptService);

//        else {
//            if (!(cardOpt instanceof DiscountCardFactory)) {
//                DiscountCard discountCard = new BronzeDiscountCard(cardOpt);
//                receipt.setDiscountCard(discountCard);
//            }
//        }


        String viewReceipt = formatter.format(receipt);

        System.out.println(viewReceipt);
    }

}
