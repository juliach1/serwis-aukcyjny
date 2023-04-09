package com.aukcje.service.iface;

import com.aukcje.dto.UserAuctionDTO;

import java.util.List;

public interface UserAuctionService {

    List<UserAuctionDTO> findAllByOfferId(Long offerId);

    UserAuctionDTO findOneWithHighestValue(Long offerId);
}
