package com.aukcje.repository;

import com.aukcje.entity.Offer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {

    @Query("Select o from Offer o where o.offerType.id = ?1 and o.offerStatus.id = 1 order by o.insertDate desc")
    Page<Offer> findNewByOfferTypeId(Integer typeId, Pageable pageable);

}
