package com.aukcje.repository;
import com.aukcje.entity.UserFavoriteOffer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserFavoriteOfferRepository extends JpaRepository<UserFavoriteOffer, Long> {

    Page<UserFavoriteOffer> findByUserId(Long userId, Pageable pageable);
    UserFavoriteOffer findByUserIdAndOfferId(Long userId, Long offerId);
    void deleteByUserIdAndOfferId(Long userId, Long offerId);
    void deleteByOfferId(Long offerId);
}
