package com.aukcje.model.mapper;

import com.aukcje.entity.*;
import com.aukcje.model.OfferAddModel;
import com.aukcje.repository.*;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

@Mapper(
        unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE,
        componentModel = "spring",
        uses = {
                // My other mappers...
        })
public abstract class OfferMapper {




    @Named("offer")
    public static Offer offer(OfferAddModel offerModel, Long userId){
        Offer offer = new Offer();

        offer.setPrice(Double.valueOf(offerModel.getPrice()));
        offer.setQuantity(offerModel.getQuantity());
        offer.setOfferDetails(offerDetails(offerModel));
        offer.setInsertDate(LocalDateTime.now());
        offer.setViews(0l);


        return offer;
    }

    private static OfferDetails offerDetails(OfferAddModel offerAddModel){
        OfferDetails offerDetails = new OfferDetails();
        offerDetails.setTitle(offerAddModel.getTitle());
        offerDetails.setDescription(offerAddModel.getDescription());

        return offerDetails;
    }
}
