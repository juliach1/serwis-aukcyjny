package com.aukcje.service.iface;

import com.aukcje.dto.UserFavoriteOfferDTO;
import com.aukcje.exception.customException.OfferNotFoundException;
import com.aukcje.exception.customException.UserNotFoundException;

import java.util.List;

public interface UserFavoriteOfferService {
    List<UserFavoriteOfferDTO> getAllByUserId(Long userId);

    void add(Long offerId, Long userId) throws UserNotFoundException, OfferNotFoundException;

    void remove(Long offerId, Long userId);

}
