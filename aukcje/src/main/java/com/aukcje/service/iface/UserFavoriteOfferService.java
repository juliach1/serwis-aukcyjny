package com.aukcje.service.iface;

import com.aukcje.dto.UserFavoriteOfferServiceDTO;

import java.util.List;

public interface UserFavoriteOfferService {
    List<UserFavoriteOfferServiceDTO> getAllByUserId(Long userId);

}
