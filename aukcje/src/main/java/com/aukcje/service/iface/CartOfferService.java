package com.aukcje.service.iface;

import com.aukcje.dto.CartOfferDTO;

import java.util.List;

public interface CartOfferService {

    List<CartOfferDTO> getAll(Long userId);

    CartOfferDTO getOne(Long cartOfferId);

    void add(Long userId, Long offerId, Integer pcs);

    void delete(Long cartOfferId);

    void deleteAllByOfferId(Long offerId);

    void changeQuantity(Long cartOfferId, Integer pcs);

    boolean isCartOfferAssignedToUser(Long userId, Long cartOfferId);
}
