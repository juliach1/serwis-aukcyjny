package com.aukcje.model.mapper;

import com.aukcje.entity.Offer;
import com.aukcje.model.OfferAddModel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OfferAddMapper {

    OfferAddMapper instance = Mappers.getMapper(OfferAddMapper.class);

//    Offer offer = new Offer(OfferAddModel offerAddModel);
}
