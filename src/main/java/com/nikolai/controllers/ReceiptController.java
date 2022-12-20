package com.nikolai.controllers;

import com.nikolai.dto.ExceptionResponse;
import com.nikolai.exceptions.UnsupportedPatternException;
import com.nikolai.facade.ReceiptCreator;
import com.nikolai.parser.WebReceiptParser;
import com.nikolai.service.DiscountCardService;
import com.nikolai.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReceiptController {
    private final DiscountCardService cardService;
    private final ProductService productService;
    private final ReceiptCreator receiptCreator;


    @Autowired
    public ReceiptController(DiscountCardService cardService,
                             ProductService productService,
                             WebReceiptParser parser,
                             ReceiptCreator receiptCreator) {
        this.cardService = cardService;
        this.productService = productService;
        this.receiptCreator = receiptCreator;
    }


    @GetMapping("/check")
    @ResponseBody
    public ResponseEntity<?> createReceipt(final HttpServletRequest request) {
        try {
            var text = request.getQueryString();
            if (!StringUtils.hasText(text)) {
                throw new UnsupportedPatternException();
            }
            receiptCreator.createReceipt(text);
            return ResponseEntity.ok().build();
        } catch (Exception exception) {
            System.err.println("Error");
            var response = new ExceptionResponse("Error");
            return ResponseEntity.badRequest().body(response);
        }
    }


}
