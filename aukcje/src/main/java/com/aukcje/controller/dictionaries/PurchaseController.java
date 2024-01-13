package com.aukcje.controller.dictionaries;

import com.aukcje.dto.OfferPurchaseInfoDTO;
import com.aukcje.dto.PurchaseStatusDTO;
import com.aukcje.dto.UserDTO;
import com.aukcje.exception.OfferNotActiveException;
import com.aukcje.exception.PurchaseStatusNotFoundException;
import com.aukcje.exception.customException.AddressNotFoundException;
import com.aukcje.exception.customException.OfferNotFoundException;
import com.aukcje.exception.customException.OfferStatusNotFoundException;
import com.aukcje.exception.customException.UserNotFoundException;
import com.aukcje.model.OfferPurchaseModel;
import com.aukcje.service.iface.OfferPurchaseService;
import com.aukcje.service.iface.PurchaseStatusService;
import com.aukcje.service.iface.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/zakup")
public class PurchaseController {

    @Autowired
    private OfferPurchaseService offerPurchaseService;

    @Autowired
    private PurchaseStatusService purchaseStatusService;

    @Autowired
    private UserService userService;

    @PostMapping("/przetworz")
    public String newPurchase(
            Principal principal,
            @RequestBody List<OfferPurchaseModel> offerPurchaseModel,
            @RequestParam("adresId") Long addressId
    ) throws UserNotFoundException, OfferNotActiveException, AddressNotFoundException, OfferNotFoundException, PurchaseStatusNotFoundException, OfferStatusNotFoundException {

        UserDTO userDTO = userService.findByUsername(principal.getName());
        offerPurchaseService.purchaseItems(offerPurchaseModel, userDTO.getId(), addressId);

        return "redirect:/uzytkownik/strona-glowna";
    }

    @GetMapping("/moje-zakupy")
    public String userOffers(HttpServletRequest request, Principal principal, Model model){

        UserDTO user = userService.findByUsername(principal.getName());
        List<OfferPurchaseInfoDTO> purchasedItems = offerPurchaseService.getAllByBuyerId(user.getId());

        List<PurchaseStatusDTO> purchaseStatuses = purchaseStatusService.findAll();

        model.addAttribute("purchaseDTOS", purchasedItems);
        model.addAttribute("purchaseStatusDTOS", purchaseStatuses);
        request.setAttribute("bought", true);


        return "/views/user/offer/purchase/user-purchases";

    }

    @GetMapping("/sprzedane")
    public String userSoldOffers(HttpServletRequest request, Principal principal, Model model){

        UserDTO user = userService.findByUsername(principal.getName());
        List<OfferPurchaseInfoDTO> purchasedItems = offerPurchaseService.getAllBySellerId(user.getId());

        List<PurchaseStatusDTO> purchaseStatuses = purchaseStatusService.findAll();

        model.addAttribute("purchaseDTOS", purchasedItems);
        model.addAttribute("purchaseStatusDTOS", purchaseStatuses);
        request.setAttribute("sold", true);

        return "/views/user/offer/purchase/user-purchases";

    }

}
