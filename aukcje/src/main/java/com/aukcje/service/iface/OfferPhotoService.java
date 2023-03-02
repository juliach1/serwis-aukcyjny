package com.aukcje.service.iface;

import com.aukcje.dto.OfferPhotoDTO;

import java.util.List;

public interface OfferPhotoService {

    List<OfferPhotoDTO> findByOfferId(Long offerId);
}
