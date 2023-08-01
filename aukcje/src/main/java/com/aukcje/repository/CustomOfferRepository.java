package com.aukcje.repository;

import com.aukcje.entity.Offer;
import com.aukcje.model.OfferSearchModel;
import org.springframework.data.domain.Page;

public interface CustomOfferRepository {

    Page<Offer> findByOfferSearchModel(OfferSearchModel osm);
}
