package com.nikolai.cardTests;


import com.nikolai.model.card.StandardDiscountCard;
import com.nikolai.repository.DiscountCardRepository;
import com.nikolai.service.DiscountCardService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class DiscountCardServiceTest {

    @InjectMocks
    private DiscountCardService service;

    @Mock
    private DiscountCardRepository repository;

    @Captor
    private ArgumentCaptor<Integer> argumentCaptor;


    @Test
    void whenFindCardByCode_thenReturnCard() {
        var card = new StandardDiscountCard(1, 1, 1234);
        Mockito.doReturn(Optional.of(card)).when(repository).findByCode(card.getCode());

        Assertions.assertSame(card, service.findCardByCode(card.getCode()).get());
    }

    @Test
    void whenFindCardById_thenReturnCard() {
        var card = new StandardDiscountCard(1, 1, 1234);
        Mockito.doReturn(Optional.of(card)).when(repository).findByCode(card.getId());

        Assertions.assertSame(card, service.findCardByCode(card.getId()).get());
    }


    @Test
    void whenFindCardByCode_expectCallRepositoryFindByCode() {
        int expectedCode = 1234;
        service.findCardByCode(expectedCode);

        Mockito.verify(repository).findByCode(argumentCaptor.capture());

        Assertions.assertEquals(expectedCode, argumentCaptor.getValue());
    }

    @Test
    void whenFindCardById_expectCallRepositoryFindById() {
        int expectedId = 1;
        service.findCardById(expectedId);

        Mockito.verify(repository).findById(argumentCaptor.capture());

        Assertions.assertEquals(expectedId, argumentCaptor.getValue());
    }


}
