package com.aukcje.service.iface;

import com.aukcje.dto.OfferDTO;
import com.aukcje.model.AddressModel;
import com.aukcje.model.OfferAddModel;
import com.aukcje.model.OfferSearchModel;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface OfferService {

    OfferDTO findById(Long id);

    List<OfferDTO> findNewAuctions(Integer pageSize);

    List<OfferDTO> findNewBuyNow(Integer pageSize);

    List<OfferDTO> findByOfferSearchModel(OfferSearchModel offerSearchModel);

    Boolean isOfferTypeAuction(OfferDTO offer);

    Boolean isOfferAssignedToUser(Long userId, Long offerId);

    Long save(OfferAddModel offerAddModel, Long userId, MultipartFile multipartFile);

    void update(OfferAddModel offerModel, Long userId, MultipartFile file);

}