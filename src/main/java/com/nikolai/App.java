package com.nikolai;

import com.nikolai.facade.ReceiptCreator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App {


    public static void main(String[] args) {
        var context = SpringApplication.run(App.class, args);
        var receiptCreator = context.getBean("receiptCreator", ReceiptCreator.class);

        receiptCreator.createReceipt(String.join(" ", args));
    }

}
