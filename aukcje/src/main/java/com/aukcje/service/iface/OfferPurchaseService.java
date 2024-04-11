package com.aukcje.service.iface;

import com.aukcje.dto.OfferPurchaseInfoDTO;
import com.aukcje.dto.UserDTO;
import com.aukcje.entity.UserRating;
import com.aukcje.enums.PurchaseStatusEnum;
import com.aukcje.exception.customException.OfferNotActiveException;
import com.aukcje.exception.customException.PurchaseStatusNotFoundException;
import com.aukcje.exception.customException.*;
import com.aukcje.model.OfferPurchaseModel;

import java.util.List;
import java.util.Objects;

public interface OfferPurchaseService {

    void setRating(Long purchaseId, UserRating userRating) throws PurchaseNotFoundException;

    void purchaseItems(List<OfferPurchaseModel> offerPurchaseModels, Long buyerId, Long addressId) throws UserNotFoundException, AddressNotFoundException, OfferNotFoundException, OfferNotActiveException, PurchaseStatusNotFoundException, OfferStatusNotFoundException;

    OfferPurchaseInfoDTO getById(Long id);

    List<OfferPurchaseInfoDTO> getAllByBuyerId(Long userId);

    List<OfferPurchaseInfoDTO> getAllBySellerId(Long userId);

    void updatePurchaseStatus(Long offerPurchaseId, Integer purchaseStatus);

    static Boolean isUserBuyer(UserDTO user, OfferPurchaseInfoDTO purchaseInfoDTO) {
        return user.getId() == purchaseInfoDTO.getBuyer().getId();
    }

    static Boolean isUserSeller(UserDTO user, OfferPurchaseInfoDTO purchaseInfoDTO) {
        return user.getId() == purchaseInfoDTO.getSeller().getId();
    }

    static Boolean isStatus(PurchaseStatusEnum status, OfferPurchaseInfoDTO offerPurchaseInfoDTO) {
        System.out.println("status to "+status.toString());
        System.out.println(Objects.equals(offerPurchaseInfoDTO.getPurchaseStatus().getName(), status.toString()));

        return Objects.equals(offerPurchaseInfoDTO.getPurchaseStatus().getName(), status.toString());
    }
}
