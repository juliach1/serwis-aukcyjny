package com.aukcje.dto.mapper;

import com.aukcje.dto.*;
import com.aukcje.entity.*;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

public interface UserFavoriteOfferDTOMapper {
    UserFavoriteOfferDTOMapper instance = Mappers.getMapper(UserFavoriteOfferDTOMapper.class);

    @Mappings({})
    UserFavoriteOfferDTO userFavoriteOfferDTO(UserFavoriteOffer userFavoriteOffer);

    default UserDTO map(User user){
        return UserDTOMapper.instance.userDTO(user);
    }
    default OfferDTO map(Offer offer){ return OfferDTOMapper.instance.offerDTO(offer);}
}
