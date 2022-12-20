package com.nikolai.facade;

import com.nikolai.factory.DiscountCardFactory;
import com.nikolai.model.Receipt;
import com.nikolai.model.card.DiscountCard;
import com.nikolai.parser.CommandLineReceiptParser;
import com.nikolai.parser.FileReceiptParser;
import com.nikolai.parser.ParserProvider;
import com.nikolai.parser.WebReceiptParser;
import com.nikolai.service.ReceiptService;
import com.nikolai.util.ReceiptFormatter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;


@Component("receiptCreator")
public class ReceiptCreator {

    private final DiscountCardFactory cardFactory;

    private final WebReceiptParser webReceiptParser;
    private final FileReceiptParser fileReceiptParser;
    private final CommandLineReceiptParser commandLineReceiptParser;
    private final ParserProvider<Receipt> provider;


    public ReceiptCreator(@Qualifier("zeroDiscountFactory") DiscountCardFactory cardFactory,
                          WebReceiptParser webReceiptParser,
                          FileReceiptParser fileReceiptParser,
                          CommandLineReceiptParser commandLineReceiptParser,
                          @Qualifier("receiptParserProvider") ParserProvider<Receipt> provider) {
        this.cardFactory = cardFactory;
        this.webReceiptParser = webReceiptParser;
        this.fileReceiptParser = fileReceiptParser;
        this.commandLineReceiptParser = commandLineReceiptParser;
        this.provider = provider;
    }


    public void createReceipt(String text) {
        provider.register(
                List.of(
                        webReceiptParser,
                        fileReceiptParser,
                        commandLineReceiptParser
                )
        );

        ReceiptFormatter formatter = null;

        Receipt receipt = provider.parse(text);

        DiscountCard cardOpt = receipt.getDiscountCard();

        if (cardOpt == null) {
            DiscountCard discountCard = cardFactory.produce();
            receipt.setDiscountCard(discountCard);
        }

        ReceiptService receiptService = new ReceiptService(receipt);
        formatter = new ReceiptFormatter(receiptService);

        String viewReceipt = formatter.format(receipt);

        System.out.println(viewReceipt);
    }

}
