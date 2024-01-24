package com.aukcje.service.iface;

import com.aukcje.dto.CartOfferDTO;
import com.aukcje.exception.customException.OfferNotFoundException;
import com.aukcje.exception.customException.OfferStatusNotFoundException;
import com.aukcje.exception.customException.UserNotFoundException;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CartOfferService {

    List<CartOfferDTO> getAll(Long userId);

    CartOfferDTO getOne(Long cartOfferId);

    Integer add(Long userId, Long offerId, Integer pcs) throws OfferNotFoundException, OfferStatusNotFoundException, UserNotFoundException;

    void delete(Long cartOfferId);

    void deleteAllByOfferId(Long offerId);

    void changeQuantity(Long cartOfferId, Integer pcs);

    boolean isCartOfferAssignedToUser(Long userId, Long cartOfferId);
}
