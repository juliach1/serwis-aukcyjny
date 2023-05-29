package com.aukcje.dto.mapper;

import com.aukcje.dto.OfferDTO;
import com.aukcje.dto.OfferDetailsDTO;
import com.aukcje.entity.Offer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OfferDTOMapper {

    OfferDTOMapper instance = Mappers.getMapper(OfferDTOMapper.class);


    @Mappings({})
    OfferDTO offerDTO(Offer offer);

}
