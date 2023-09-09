package com.aukcje.service.iface;

import com.aukcje.dto.OfferDTO;
import com.aukcje.model.OfferAddModel;
import com.aukcje.model.OfferSearchModel;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface OfferService {

    OfferDTO findById(Long id);

    List<OfferDTO> findByUserId(Long userId);

    List<OfferDTO> findNewAuctions(Integer pageSize);

    List<OfferDTO> findNewBuyNow(Integer pageSize);

    List<OfferDTO> findByOfferSearchModel(OfferSearchModel offerSearchModel);

    Long save(OfferAddModel offerAddModel, Long userId, MultipartFile multipartFile);

    void update(OfferAddModel offerModel, Long userId, MultipartFile file);

    void delete(Long offerId);

    Boolean isOfferTypeAuction(OfferDTO offer);

    Boolean isOfferAssignedToUser(Long userId, Long offerId);

    void setIsFavorite(List<OfferDTO> offerDTOS, Long userId);

}