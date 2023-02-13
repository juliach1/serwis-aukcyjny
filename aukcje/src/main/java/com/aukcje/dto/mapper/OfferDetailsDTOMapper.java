package com.aukcje.dto.mapper;

import com.aukcje.dto.OfferDetailsDTO;
import com.aukcje.entity.OfferDetails;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OfferDetailsDTOMapper {

    OfferDetailsDTOMapper instance = Mappers.getMapper(OfferDetailsDTOMapper.class);

    @Mappings({})
    OfferDetailsDTO offerDetailsDTO(OfferDetails offerDetails);
}
