package com.aukcje.dto.mapper;

import com.aukcje.dto.OfferStatusDTO;
import com.aukcje.entity.OfferStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OfferStatusDTOMapper {

    OfferStatusDTOMapper instance = Mappers.getMapper(OfferStatusDTOMapper.class);

    @Mappings({})
    OfferStatusDTO offerStatusDTO(OfferStatus offerStatus);
}
