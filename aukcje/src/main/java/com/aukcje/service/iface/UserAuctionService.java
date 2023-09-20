package com.aukcje.service.iface;

import com.aukcje.dto.UserAuctionDTO;
import com.aukcje.enums.BidStatusEnum;
import com.aukcje.exception.OfferNotActiveException;
import com.aukcje.exception.customException.CanNotBidYourOfferException;
import com.aukcje.exception.customException.OfferNotFoundException;
import com.aukcje.exception.customException.UserNotActiveException;
import com.aukcje.exception.customException.UserNotFoundException;

import java.util.List;

public interface UserAuctionService {

    List<UserAuctionDTO> findAllByOfferId(Long offerId);

    UserAuctionDTO findOneWithHighestValue(Long offerId);

    BidStatusEnum placeBid(Long offerId, Double value, Long userId) throws OfferNotFoundException, OfferNotActiveException, UserNotFoundException, UserNotActiveException, CanNotBidYourOfferException;
}
