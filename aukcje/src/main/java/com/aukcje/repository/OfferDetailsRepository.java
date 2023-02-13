package com.aukcje.repository;

import com.aukcje.entity.OfferDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfferDetailsRepository extends JpaRepository<OfferDetails, Long> {
}
