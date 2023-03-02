package com.aukcje.repository;

import com.aukcje.entity.OfferPhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfferPhotoRepository extends JpaRepository<OfferPhoto, Long> {

    List<OfferPhoto> getByOfferId(Long offerId);
}
