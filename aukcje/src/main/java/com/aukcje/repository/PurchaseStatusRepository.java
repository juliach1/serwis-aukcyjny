package com.aukcje.repository;

import com.aukcje.entity.PurchaseStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseStatusRepository extends JpaRepository<PurchaseStatus, Integer> {
}
