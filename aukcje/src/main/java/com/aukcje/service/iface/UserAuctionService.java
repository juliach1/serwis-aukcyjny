package com.aukcje.service.iface;

import com.aukcje.dto.OfferDTO;
import com.aukcje.dto.UserAuctionDTO;
import com.aukcje.enums.BidStatusEnum;
import com.aukcje.exception.OfferNotActiveException;
import com.aukcje.exception.customException.*;

import java.util.List;

public interface UserAuctionService {

    List<UserAuctionDTO> findAllByOfferId(Long offerId);

    UserAuctionDTO findOneWithHighestValue(Long offerId);

    BidStatusEnum placeBid(Long offerId, Double value, Long userId) throws OfferNotFoundException, OfferNotActiveException, UserNotFoundException, UserNotActiveException, CanNotBidYourOfferException, OfferStatusNotFoundException;

    boolean hasUserAnyAuctions(OfferDTO offerDTO);
}
