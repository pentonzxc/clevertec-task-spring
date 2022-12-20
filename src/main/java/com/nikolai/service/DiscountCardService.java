package com.nikolai.service;

import com.nikolai.model.card.StandardDiscountCard;
import com.nikolai.repository.DiscountCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DiscountCardService {
    private final DiscountCardRepository cardRepository;

    @Autowired
    public DiscountCardService(DiscountCardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public Optional<StandardDiscountCard> findCardByCode(Integer code) {
        return cardRepository.findByCode(code);
    }

    public Optional<StandardDiscountCard> findCardById(Integer id) {
        return cardRepository.findById(id);
    }


//    public DiscountCard function(Function<>)
}
