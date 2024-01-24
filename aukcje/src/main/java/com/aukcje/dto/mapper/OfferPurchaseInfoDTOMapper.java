package com.aukcje.dto.mapper;

import com.aukcje.dto.OfferDTO;
import com.aukcje.dto.OfferPurchaseInfoDTO;
import com.aukcje.entity.Offer;
import com.aukcje.entity.OfferPurchaseInfo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OfferPurchaseInfoDTOMapper {

    OfferPurchaseInfoDTOMapper instance = Mappers.getMapper(OfferPurchaseInfoDTOMapper.class);

    OfferPurchaseInfoDTO offerPurchaseInfoDTO(OfferPurchaseInfo offerPurchaseInfo);

    default OfferDTO map(Offer offer){
        return OfferDTOMapper.instance.offerDTO(offer);
    }

//        default OfferStatusDTO map(OfferStatus offerStatus){ return OfferStatusDTOMapper.instance.offerStatusDTO(offerStatus); }
}
