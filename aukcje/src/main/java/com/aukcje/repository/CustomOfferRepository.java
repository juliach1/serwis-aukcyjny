package com.aukcje.repository;

import com.aukcje.entity.Offer;
import com.aukcje.model.OfferSearchModel;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

public interface CustomOfferRepository {

    Page<Offer> findByOfferSearchModel(OfferSearchModel osm);
}
