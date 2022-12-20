package com.nikolai.repository;

import com.nikolai.model.card.StandardDiscountCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DiscountCardRepository extends JpaRepository<StandardDiscountCard, Integer> {
    Optional<StandardDiscountCard> findByCode(Integer code);

    Optional<StandardDiscountCard> findById(Integer id);
}
