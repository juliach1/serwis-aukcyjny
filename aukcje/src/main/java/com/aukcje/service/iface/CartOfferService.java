package com.aukcje.service.iface;

import com.aukcje.dto.CartOfferDTO;

import java.util.List;

public interface CartOfferService {

    List<CartOfferDTO> getAll(Long userId);

    void add(Long userId, Long offerId, Integer pcs);
}
