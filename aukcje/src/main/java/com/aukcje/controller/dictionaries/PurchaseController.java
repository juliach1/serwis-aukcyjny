package com.aukcje.controller.dictionaries;

import com.aukcje.dto.UserDTO;
import com.aukcje.exception.OfferNotActiveException;
import com.aukcje.exception.PurchaseStatusNotFoundException;
import com.aukcje.exception.customException.AddressNotFoundException;
import com.aukcje.exception.customException.OfferNotFoundException;
import com.aukcje.exception.customException.OfferStatusNotFoundException;
import com.aukcje.exception.customException.UserNotFoundException;
import com.aukcje.model.OfferPurchaseModel;
import com.aukcje.service.iface.OfferPurchaseService;
import com.aukcje.service.iface.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/zakup")
public class PurchaseController {

    @Autowired
    private OfferPurchaseService offerPurchaseService;

    @Autowired
    private UserService userService;

    @PostMapping("/przetworz")
    public String newPurchase(
            Principal principal,
            @RequestBody List<OfferPurchaseModel> offerPurchaseModel,
            @RequestParam("adresId") Long addressId
    ) throws UserNotFoundException, OfferNotActiveException, AddressNotFoundException, OfferNotFoundException, PurchaseStatusNotFoundException, OfferStatusNotFoundException {
        System.out.println("W PRZETWARZANIU KONTEOLERA KUPOWANIA");

        UserDTO userDTO = userService.findByUsername(principal.getName());
        offerPurchaseService.purchaseItems(offerPurchaseModel, userDTO.getId(), addressId);

        return "redirect:/uzytkownik/strona-glowna";
    }

}
