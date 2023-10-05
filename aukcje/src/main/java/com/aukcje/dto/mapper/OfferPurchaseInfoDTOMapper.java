package com.aukcje.dto.mapper;

import com.aukcje.dto.*;
import com.aukcje.entity.Offer;
import com.aukcje.entity.OfferPurchaseInfo;
import com.aukcje.entity.PurchaseStatus;
import com.aukcje.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
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
