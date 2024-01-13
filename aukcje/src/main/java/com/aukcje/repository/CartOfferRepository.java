package com.aukcje.repository;

import com.aukcje.entity.CartOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartOfferRepository  extends JpaRepository<CartOffer, Long> {

    List<CartOffer> findAllByUserId(Long userId);

    CartOffer findByOfferIdAndUserId(Long id, Long userId);

    void deleteAllByOfferId(Long offerId);

}
