package com.aukcje.dto.mapper;

import com.aukcje.dto.OfferDTO;
import com.aukcje.dto.UserDTO;
import com.aukcje.dto.UserFavoriteOfferDTO;
import com.aukcje.entity.Offer;
import com.aukcje.entity.User;
import com.aukcje.entity.UserFavoriteOffer;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserFavoriteOfferDTOMapper {

    UserFavoriteOfferDTOMapper instance = Mappers.getMapper(UserFavoriteOfferDTOMapper.class);

    @Mappings({})
    UserFavoriteOfferDTO userFavoriteOfferDTO(UserFavoriteOffer userFavoriteOffer);

    default UserDTO map(User user){
        return UserDTOMapper.instance.userDTO(user);
    }
    default OfferDTO map(Offer offer) {
        OfferDTO offerDTO = OfferDTOMapper.instance.offerDTO(offer);
        offerDTO.setIsFavorite(true);
        return offerDTO;
    }
}
