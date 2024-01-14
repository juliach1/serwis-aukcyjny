package com.aukcje.repository;

import com.aukcje.entity.Offer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {

    Page<Offer> findByOfferTypeIdOrderByInsertDateDesc(Integer typeId, Pageable pageable);

    Page<Offer> findByUserIdAndOfferTypeIdAndOfferStatusIdOrderByInsertDateDesc(Long userId, Integer typeId, Integer statusId, Pageable pageable);
    List<Offer> findByUserId(Long userId);

    Integer countOfferByUserIdAndOfferStatusId(Long userId, Integer statusId);
}
