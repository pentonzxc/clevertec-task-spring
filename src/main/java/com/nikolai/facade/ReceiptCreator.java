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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;


@Component("receiptCreator")
public class ReceiptCreator {

    private final DiscountCardFactory cardFactory;
    private final WebReceiptParser webReceiptParser;
    private final FileReceiptParser fileReceiptParser;
    private final CommandLineReceiptParser commandLineReceiptParser;
    private final ParserProvider<Receipt> provider;
    private final Environment env;


    public ReceiptCreator(@Qualifier("zeroDiscountFactory") DiscountCardFactory cardFactory,
                          WebReceiptParser webReceiptParser,
                          FileReceiptParser fileReceiptParser,
                          CommandLineReceiptParser commandLineReceiptParser,
                          @Qualifier("receiptParserProvider") ParserProvider<Receipt> provider,
                          Environment env) {
        this.cardFactory = cardFactory;
        this.webReceiptParser = webReceiptParser;
        this.fileReceiptParser = fileReceiptParser;
        this.commandLineReceiptParser = commandLineReceiptParser;
        this.provider = provider;
        this.env = env;
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

        writeReceiptInConsole(viewReceipt);

        writeReceiptInFile(viewReceipt);

    }


    private void writeReceiptInConsole(String receipt) {
        System.out.println(receipt);
    }

    private void writeReceiptInFile(String receipt) {
        var path = env.getProperty("receiptFile.out");

        File file = new File(path);
        try {
            file.createNewFile();
            Files.writeString(Paths.get(path), receipt, StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING);
        } catch (Exception ignored) {
        }
    }

}
