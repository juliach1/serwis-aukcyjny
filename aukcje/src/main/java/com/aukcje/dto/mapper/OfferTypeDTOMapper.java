package com.aukcje.dto.mapper;

import com.aukcje.dto.OfferTypeDTO;
import com.aukcje.entity.OfferType;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OfferTypeDTOMapper {

    OfferTypeDTOMapper instance = Mappers.getMapper(OfferTypeDTOMapper.class);

    @Mappings({})
    OfferTypeDTO offerTypeDTO(OfferType offerType);
}
