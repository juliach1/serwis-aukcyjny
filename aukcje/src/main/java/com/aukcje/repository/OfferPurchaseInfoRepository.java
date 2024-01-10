package com.aukcje.repository;

import com.aukcje.entity.OfferPurchaseInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OfferPurchaseInfoRepository extends JpaRepository<OfferPurchaseInfo, Long> {

    List<OfferPurchaseInfo> findOfferPurchaseInfoByBuyerIdOrderByPurchaseTimeDesc(Long buyerId);

    List<OfferPurchaseInfo> findOfferPurchaseInfoBySellerIdOrderByPurchaseTimeDesc(Long userId);
}
