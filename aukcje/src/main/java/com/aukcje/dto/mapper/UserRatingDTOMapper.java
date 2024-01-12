package com.aukcje.dto.mapper;

import com.aukcje.dto.UserRatingDTO;
import com.aukcje.entity.UserRating;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserRatingDTOMapper {

    UserRatingDTOMapper instance = Mappers.getMapper(UserRatingDTOMapper.class);

    @Mappings({})
    UserRatingDTO userRatingDTO(UserRating userRating);
}

