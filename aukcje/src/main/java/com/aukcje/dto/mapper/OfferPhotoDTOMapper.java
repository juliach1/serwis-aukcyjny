package com.aukcje.dto.mapper;

import com.aukcje.dto.OfferPhotoDTO;
import com.aukcje.entity.OfferPhoto;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OfferPhotoDTOMapper {

    OfferPhotoDTOMapper instance = Mappers.getMapper(OfferPhotoDTOMapper.class);

    @Mappings({})
    OfferPhotoDTO offerPhotoDTO(OfferPhoto offerPhoto);
}
