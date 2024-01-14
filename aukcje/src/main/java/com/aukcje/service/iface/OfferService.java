package com.aukcje.service.iface;

import com.aukcje.dto.OfferDTO;
import com.aukcje.entity.Offer;
import com.aukcje.exception.customException.OfferNotFoundException;
import com.aukcje.exception.customException.OfferStatusNotFoundException;
import com.aukcje.model.OfferAddModel;
import com.aukcje.model.OfferSearchModel;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface OfferService {

    OfferDTO findById(Long id) throws OfferNotFoundException, OfferStatusNotFoundException;

    List<OfferDTO> findByUserId(Long userId) throws OfferStatusNotFoundException;

    List<OfferDTO> findNewAuctions(Integer pageSize) throws OfferStatusNotFoundException;

    List<OfferDTO> findNewBuyNow(Integer pagedSize) throws OfferStatusNotFoundException;

    List<OfferDTO> findActiveAuctionsByUserId(Long userId, Integer pageSize) throws OfferStatusNotFoundException;

    List<OfferDTO> findActiveBuyNowByUserId(Long userId, Integer pageSize) throws OfferStatusNotFoundException;

    List<OfferDTO> findByOfferSearchModel(OfferSearchModel offerSearchModel) throws OfferStatusNotFoundException;

    List<OfferDTO> findFavoriteForUser(Long userId, Integer pageSize);

    Long save(OfferAddModel offerAddModel, Long userId, MultipartFile multipartFile);

    void update(OfferAddModel offerModel, Long userId, MultipartFile file);

    void delete(Long offerId);

    Integer getActiveOffersNumberByUserId(Long userId);

    Boolean isOfferTypeAuction(OfferDTO offer);

    Boolean isOfferAssignedToUser(Long userId, Long offerId);

    void setIsFavorite(List<OfferDTO> offerDTOS, Long userId);

    boolean isOfferActive(Offer offer);

}