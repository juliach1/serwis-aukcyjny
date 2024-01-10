package com.aukcje.service.iface;

import com.aukcje.dto.OfferPurchaseInfoDTO;
import com.aukcje.dto.UserDTO;
import com.aukcje.enums.PurchaseStatusEnum;
import com.aukcje.exception.OfferNotActiveException;
import com.aukcje.exception.PurchaseStatusNotFoundException;
import com.aukcje.exception.customException.AddressNotFoundException;
import com.aukcje.exception.customException.OfferNotFoundException;
import com.aukcje.exception.customException.OfferStatusNotFoundException;
import com.aukcje.exception.customException.UserNotFoundException;
import com.aukcje.model.OfferPurchaseModel;

import java.util.List;
import java.util.Objects;

public interface OfferPurchaseService {

    //TODO DOKOŃCZYĆ

    void purchaseItems(List<OfferPurchaseModel> offerPurchaseModels, Long buyerId, Long addressId) throws UserNotFoundException, AddressNotFoundException, OfferNotFoundException, OfferNotActiveException, PurchaseStatusNotFoundException, OfferStatusNotFoundException;

    OfferPurchaseInfoDTO getById(Long id);

    List<OfferPurchaseInfoDTO> getAllByUserId(Long userId);

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
