package com.aukcje.repository;
import com.aukcje.entity.UserFavoriteOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserFavoriteOfferRepository extends JpaRepository<UserFavoriteOffer, Long> {

    List<UserFavoriteOffer> findByUserId(Long userId);
    UserFavoriteOffer findByUserIdAndOfferId(Long userId, Long offerId);
    boolean deleteByUserIdAndOfferId(Long userId, Long offerId);
}
