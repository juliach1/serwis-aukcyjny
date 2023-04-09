package com.aukcje.repository;

import com.aukcje.entity.Offer;
import com.aukcje.entity.User;
import com.aukcje.entity.UserAuction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface UserAuctionRepository extends JpaRepository<UserAuction,Long> {

    UserAuction findFirstByOfferOrderByValueDesc(Offer offer);
    List<UserAuction> findAllByOfferId(Long offerId);
}
