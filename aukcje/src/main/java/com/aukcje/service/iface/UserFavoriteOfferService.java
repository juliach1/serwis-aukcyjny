package com.aukcje.service.iface;

import com.aukcje.dto.UserFavoriteOfferDTO;
import com.aukcje.exception.customException.OfferNotFoundException;
import com.aukcje.exception.customException.UserNotFoundException;

import java.util.List;

public interface UserFavoriteOfferService {
    List<UserFavoriteOfferDTO> getActiveByUserId(Long userId, Integer pageSize);

    UserFavoriteOfferDTO geByUserIdAndOfferId(Long userId, Long offerId);

    void toggle(Long offerId, Long userId) throws UserNotFoundException, OfferNotFoundException;

    void deleteAllByOfferId(Long offerId);

    void add(Long offerId, Long userId) throws UserNotFoundException, OfferNotFoundException;
}
