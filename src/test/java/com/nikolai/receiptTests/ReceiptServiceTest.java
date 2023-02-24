package com.nikolai.receiptTests;

import com.nikolai.decorator.BronzeDiscountCard;
import com.nikolai.model.Receipt;
import com.nikolai.model.card.DiscountCard;
import com.nikolai.model.card.StandardDiscountCard;
import com.nikolai.model.card.ZeroDiscountCard;
import com.nikolai.model.product.Product;
import com.nikolai.model.product.ProductOrder;
import com.nikolai.service.ReceiptService;
import com.nikolai.util.ProductDiscountResolver;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ReceiptServiceTest {

    ReceiptService receiptService;

    Receipt receipt;

    @Captor
    ArgumentCaptor<Integer> integerArgumentCaptor;

    @BeforeEach
    public void init() {
        receipt = new Receipt();
        receiptService = new ReceiptService(receipt);
    }


    @Test
    public void whenZeroDiscount_thenSummaryPriceReturnSummary() {
        var product1 = new Product(1, 1d);
        var product2 = new Product(2, 2d);

        var order1 = new ProductOrder(product1, 2);
        var order2 = new ProductOrder(product1, 2);
        var order3 = new ProductOrder(product2, 3);
        receiptAddAll(order1, order2, order3);

        receipt.setDiscountCard(new ZeroDiscountCard());
        receiptService = new ReceiptService(receipt);

        var expectedSummary = product1.getPrice() * (order1.getQuantity() + order2.getQuantity()) +
                product2.getPrice() * order3.getQuantity();

        Assertions.assertEquals(expectedSummary, receiptService.summaryPrice());
    }


    @Test
    public void whenDiscount_thenSummaryDiscountedPriceReturnSummary() {
        var product1 = new Product(1, 1d);
        var product2 = new Product(2, 2d);

        var order1 = new ProductOrder(product1, 2);
        var order2 = new ProductOrder(product1, 2);
        var order3 = new ProductOrder(product2, 3);
        receiptAddAll(order1, order2, order3);

        receipt.setDiscountCard(new BronzeDiscountCard(new StandardDiscountCard(2, 20)));
        receiptService = new ReceiptService(receipt);

        var diffPrice1 = discountPrice(receipt.get(product1.getId()), receipt.getDiscountCard());
        var diffPrice2 = discountPrice(receipt.get(product2.getId()), receipt.getDiscountCard());

        var expectedSum = diffPrice1 * (order1.getQuantity() + order2.getQuantity()) +
                diffPrice2 * order3.getQuantity();

        Assertions.assertEquals(expectedSum, receiptService.summaryDiscountedPrice());
    }


    @Test
    public void whenConstructWithReceipt_thenGetReceiptReturnReceipt() {
        var expectedReceipt = new Receipt();
        receiptService = new ReceiptService(expectedReceipt);

        Assertions.assertSame(expectedReceipt, receiptService.getReceipt());
    }

    @Test
    public void whenEmptyGetReceipt_thenReturnNull() {
        receiptService = new ReceiptService(null);

        Assertions.assertNull(receiptService.getReceipt());
    }


    @Test
    public void whenZeroDiscount_thenDiscountPriceReturnPrice() {
        var product = new Product(1, 1d);
        var expectedPrice = 1d;

        receipt.add(new ProductOrder(product, 3));
        receipt.setDiscountCard(new ZeroDiscountCard());

        Assertions.assertEquals(expectedPrice, receiptService.discountPrice(product));
    }

    @Test
    public void whenDiscount_thenDiscountPriceReturnDiffPrice() {
        var product = new Product(1, 1d);

        receipt.add(new ProductOrder(product, 5));
        receipt.setDiscountCard(new BronzeDiscountCard(new StandardDiscountCard(1, 20)));
        var expectedPrice = discountPrice(receipt.get(product.getId()), receipt.getDiscountCard());

        Assertions.assertEquals(expectedPrice, receiptService.discountPrice(product));
    }


    @Test
    public void whenDiscountPriceWithProduct_expectCallDiscountPriceWithProductId() {
        receiptService = Mockito.spy(receiptService);
        var product = new Product(1 , 1d);
        receipt.add(new ProductOrder(product , 1));
        receipt.setDiscountCard(new ZeroDiscountCard());

        receiptService.discountPrice(product);
        Mockito.verify(receiptService).discountPrice(integerArgumentCaptor.capture());

        Assertions.assertEquals(product.getId(), integerArgumentCaptor.getValue());
    }

    @Test
    public void whenDiscountPriceWithIdAndDiscountPriceWithProduct_thenReturnSamePrices() {
        var product = new Product(1, 1d);
        receipt.add(new ProductOrder(product, 5));
        receipt.setDiscountCard(new StandardDiscountCard(1, 1));
        var discountPrice = receiptService.discountPrice(product);

        Assertions.assertEquals(receiptService.discountPrice(product.getId()), discountPrice);
    }


    private void receiptAddAll(ProductOrder... orders) {
        for (var order : orders) {
            receipt.add(order);
        }
    }


    private double discountPrice(ProductOrder order, DiscountCard card) {
        return order.getProduct().getPrice() * (100 - (doDiscount(order, card))) / 100;
    }

    private int doDiscount(ProductOrder order, DiscountCard card) {
        return ProductDiscountResolver.resolve(order, card);
    }
}
