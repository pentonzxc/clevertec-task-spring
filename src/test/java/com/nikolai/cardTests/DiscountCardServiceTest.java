package com.nikolai.cardTests;


import com.nikolai.model.card.StandardDiscountCard;
import com.nikolai.repository.DiscountCardRepository;
import com.nikolai.service.DiscountCardService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Optional;

public class DiscountCardServiceTest {

    @InjectMocks
    DiscountCardService service;

    @Mock
    DiscountCardRepository repository;

    @Captor
    ArgumentCaptor<Integer> argumentCaptor;


    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void whenFindCardByCode_thenReturnCard() {
        var card = new StandardDiscountCard(1, 1, 1234);
        Mockito.doReturn(Optional.of(card)).when(repository).findByCode(card.getCode());

        Assertions.assertSame(card, service.findCardByCode(card.getCode()).get());
    }

    @Test
    public void whenFindCardById_thenReturnCard() {
        var card = new StandardDiscountCard(1, 1, 1234);
        Mockito.doReturn(Optional.of(card)).when(repository).findByCode(card.getId());

        Assertions.assertSame(card, service.findCardByCode(card.getId()).get());
    }


    @Test
    public void whenFindCardByCode_expectCallRepositoryFindByCode(){
        int expectedCode = 1234;
        service.findCardByCode(expectedCode);

        Mockito.verify(repository).findByCode(argumentCaptor.capture());

        Assertions.assertEquals(expectedCode, argumentCaptor.getValue());
    }

    @Test
    public void whenFindCardById_expectCallRepositoryFindById(){
        int expectedId = 1;
        service.findCardById(expectedId);

        Mockito.verify(repository).findById(argumentCaptor.capture());

        Assertions.assertEquals(expectedId, argumentCaptor.getValue());
    }


}
