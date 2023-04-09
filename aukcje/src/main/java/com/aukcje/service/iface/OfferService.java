package com.aukcje.service.iface;

import com.aukcje.dto.OfferDTO;
import com.aukcje.dto.OfferTypeDTO;
import com.aukcje.entity.Offer;
import com.aukcje.model.OfferSearchModel;
import com.aukcje.repository.OfferRepository;
import org.springframework.data.domain.Page;

import java.util.List;

public interface OfferService {

    OfferDTO findById(Long id);
    List<OfferDTO> findNewAuctions(Integer pageSize);
    List<OfferDTO> findNewBuyNow(Integer pageSize);
    List<OfferDTO> findByOfferSearchModel(OfferSearchModel offerSearchModel);

    Boolean isOfferTypeAuction(OfferDTO offer);
}