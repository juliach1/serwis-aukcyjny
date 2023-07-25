package com.aukcje.model.mapper;

import com.aukcje.entity.*;
import com.aukcje.model.OfferAddModel;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.time.LocalDateTime;

@Mapper(
        unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE,
        componentModel = "spring",
        uses = {
                // My other mappers...
        })
public abstract class OfferMapper {

    @Named("offer")
    public static Offer offer(OfferAddModel offerModel, OfferDetails offerDetails, Category category, User user, OfferStatus offerStatus, OfferType offerType){
        Offer offer = new Offer();

        offer.setId(offerModel.getId());
        offer.setCategory(category);
        offer.setUser(user);
        offer.setInsertDate(LocalDateTime.now());
        offer.setViews(0l);
        offer.setOfferStatus(offerStatus);
        offer.setOfferType(offerType);
        offer.setOfferDetails(offerDetails);
        offer.setQuantity(offerModel.getQuantity());
        offer.setPrice(Double.valueOf(offerModel.getPrice()));

        offer.setPrice(Double.valueOf(offerModel.getPrice()));
        offer.setQuantity(offerModel.getQuantity());
        offer.setOfferDetails(offerDetails(offerModel));
        offer.setInsertDate(LocalDateTime.now());
        offer.setViews(0l);

        offer.setOfferDetails(offerDetails);


        if(offerModel.getLengthInDays() != null) {
            offer.setEndDate(offer.getInsertDate().plusDays(offerModel.getLengthInDays()));
        }


        return offer;
    }

    private static OfferDetails offerDetails(OfferAddModel offerAddModel){
        OfferDetails offerDetails = new OfferDetails();
        offerDetails.setTitle(offerAddModel.getTitle());
        offerDetails.setDescription(offerAddModel.getDescription());

        return offerDetails;
    }
}
