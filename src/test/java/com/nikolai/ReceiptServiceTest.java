package com.nikolai;

import com.nikolai.constants.ProductConstants;
import com.nikolai.factory.DiscountCardFactory;
import com.nikolai.factory.ZeroDiscountCardFactory;
import com.nikolai.model.card.StandardDiscountCard;
import com.nikolai.model.product.Product;
import com.nikolai.model.Receipt;
import com.nikolai.model.product.ProductOrder;
import com.nikolai.service.ReceiptService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class ReceiptServiceTest {

    private ReceiptService receiptService;
    private static DiscountCardFactory cardFactory;

    @BeforeAll
    public static void setUp() {
        cardFactory = new ZeroDiscountCardFactory();
    }

    @BeforeEach
    public void init() {
        Receipt receipt = new Receipt();
        receiptService = new ReceiptService(receipt);
        var discountCard = cardFactory.produce();
        receipt.setDiscountCard(discountCard);
    }


    @Test
    public void givenNewProductOrders_whenRealSummaryPrice_thenReturnSummary() {
        var product1 = new Product(2, 3D);
        var product2 = new Product(1, 2D);

        var order1 = new ProductOrder(product1, 1);
        var order2 = new ProductOrder(product1, 2);
        var order3 = new ProductOrder(product2, 3);
        var receipt = receiptService.getReceipt();

        receipt.add(order1);
        receipt.add(order2);
        receipt.add(order3);

        double expectedSum = product2.getPrice() * order3.getQuantity() +
                product1.getPrice() * (order2.getQuantity() + order1.getQuantity());
        var actualSum = receiptService.summaryPrice();

        Assertions.assertEquals(expectedSum, actualSum);

        var standardDiscountCard = new StandardDiscountCard(2, 20);
        receipt.setDiscountCard(standardDiscountCard);

        var actualWithAnotherCardSum = receiptService.summaryPrice();
        Assertions.assertEquals(expectedSum, actualWithAnotherCardSum);
    }


    @Test
    public void givenNewProductOrders_whenSummaryDiffPrice_thenReturnDiscountSummary() {
        var product1 = new Product(2, 3D);
        var product2 = new Product(1, 2D);
        var receipt = receiptService.getReceipt();

        var order1 = new ProductOrder(product1, 3);
        var order2 = new ProductOrder(product1, 2);
        var order3 = new ProductOrder(product2, 3);

        receipt.add(order1);
        receipt.add(order2);
        receipt.add(order3);

//        var product1_diff_price_1 = (product1.getPrice() * (100 - ProductConstants.WITH_QUANTITY_DISCOUNT)) / 100;
        var product1DiscountPriceForQuantity = (product1.getPrice() * (100 - ProductConstants.WITH_QUANTITY_DISCOUNT)) / 100;

        double firstExpectedSum = product2.getPrice() * order3.getQuantity() +
                product1DiscountPriceForQuantity * (order2.getQuantity() + order1.getQuantity());


        var actualSum = receiptService.summaryDiscountedPrice();


        Assertions.assertEquals(firstExpectedSum, actualSum);

        var standardDiscountCard = new StandardDiscountCard(2, 20);
        receipt.setDiscountCard(standardDiscountCard);

        var product1DiscountPriceForCardQuantity = product1.getPrice() * (100 - (ProductConstants.WITH_QUANTITY_DISCOUNT + standardDiscountCard.getDiscount())) / 100;

        var product2DiscountPriceForCard = (product2.getPrice() * (100 - standardDiscountCard.getDiscount())) / 100;


        var secondExpectedSum = product2DiscountPriceForCard * order3.getQuantity() +
                product1DiscountPriceForCardQuantity * (order2.getQuantity() + order1.getQuantity());

        var actualWithAnotherCardSum = receiptService.summaryDiscountedPrice();
        Assertions.assertEquals(secondExpectedSum, actualWithAnotherCardSum);
    }


    @Test
    public void whenGetReceipt_thenReturnReceipt() {
        Receipt srcReceipt = receiptService.getReceipt();
        var trgReceipt = receiptService.getReceipt();
        Assertions.assertSame(srcReceipt, trgReceipt);
    }


    @Test
    public void givenProduct_whenDiscountPrice_thenReturnDiscountPrice() {
        var product = new Product(1, 2D);
        var card = new StandardDiscountCard(2, 20);
        var receipt = receiptService.getReceipt();
        var price = product.getPrice();


        var order = Mockito.mock(ProductOrder.class);
        Mockito.when(order.getProduct()).thenReturn(product);
        Mockito.when(order.getQuantity()).thenReturn(1).thenReturn(6);

        receipt.setDiscountCard(card);
        receipt.add(order);


        double expectedPrice = (price * (100 - card.getDiscount())) / 100;

        Assertions.assertEquals(expectedPrice, receiptService.discountPrice(product));

        double expectedPrice2 = (price * (100 - (card.getDiscount() + ProductConstants.WITH_QUANTITY_DISCOUNT)) / 100);

        Assertions.assertEquals(expectedPrice2, receiptService.discountPrice(product));

    }

    @Test
    public void givenProductId_whenDiscountPrice_thenReturnDiscountPrice() {
        var product = new Product(1, 2D);
        var card = new StandardDiscountCard(2, 20);
        var receipt = receiptService.getReceipt();
        var price = product.getPrice();


        var order = Mockito.mock(ProductOrder.class);
        Mockito.when(order.getProduct()).thenReturn(product);
        Mockito.when(order.getQuantity()).thenReturn(1).thenReturn(6);

        receipt.setDiscountCard(card);
        receipt.add(order);


        double expectedPrice = (price * (100 - card.getDiscount())) / 100;

        Assertions.assertEquals(expectedPrice, receiptService.discountPrice(product.getId()));

        double expectedPrice2 = (price * (100 - (card.getDiscount() + ProductConstants.WITH_QUANTITY_DISCOUNT)) / 100);

        Assertions.assertEquals(expectedPrice2, receiptService.discountPrice(product.getId()));

    }


}
