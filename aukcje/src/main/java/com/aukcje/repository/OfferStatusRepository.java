package com.aukcje.repository;

import com.aukcje.entity.OfferStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OfferStatusRepository extends JpaRepository<OfferStatus, Integer> {
}
