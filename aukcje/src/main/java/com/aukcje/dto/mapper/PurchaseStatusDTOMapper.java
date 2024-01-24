package com.aukcje.dto.mapper;

import com.aukcje.dto.PurchaseStatusDTO;
import com.aukcje.entity.PurchaseStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PurchaseStatusDTOMapper {

    PurchaseStatusDTOMapper instance = Mappers.getMapper(PurchaseStatusDTOMapper.class);

    @Mappings({})
    PurchaseStatusDTO purchaseStatusDTO(PurchaseStatus purchaseStatus);

//    @Named("categoryPath")
//    default OfferDTO mapSeller(Offer seller){
//        return OfferDTOMapper.instance.offerDTO(seller);
//    }

//        default OfferStatusDTO map(OfferStatus offerStatus){ return OfferStatusDTOMapper.instance.offerStatusDTO(offerStatus); }
}
