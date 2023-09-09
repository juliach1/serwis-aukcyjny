package com.aukcje.service.iface;

import com.aukcje.dto.UserFavoriteOfferDTO;
import com.aukcje.exception.customException.OfferNotFoundException;
import com.aukcje.exception.customException.UserNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserFavoriteOfferService {
    List<UserFavoriteOfferDTO> getAllByUserId(Long userId, Integer pageSize);

    UserFavoriteOfferDTO geByUserIdAndOfferId(Long userId, Long offerId);

    void add(Long offerId, Long userId) throws UserNotFoundException, OfferNotFoundException;

    void remove(Long offerId, Long userId);

    void deleteByOfferId(Long offerId);

}
