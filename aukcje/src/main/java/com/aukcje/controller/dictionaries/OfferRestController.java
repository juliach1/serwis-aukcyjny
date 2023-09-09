package com.aukcje.controller.dictionaries;

import com.aukcje.dto.UserDTO;
import com.aukcje.exception.customException.OfferDeletePermissionDeniedException;
import com.aukcje.service.iface.OfferService;
import com.aukcje.service.iface.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/oferta")
public class OfferRestController {

    @Autowired
    UserService userService;

    @Autowired
    OfferService offerService;

    @GetMapping("/usun/{ofertaId}")
    public void deleteOffer(
            @PathVariable("ofertaId") Long offerId,
            Principal principal
    ) throws OfferDeletePermissionDeniedException {
        UserDTO user = userService.findByUsername(principal.getName());

        if(offerService.isOfferAssignedToUser(user.getId(), offerId)){
            offerService.delete(offerId);
        }else {
            throw new OfferDeletePermissionDeniedException();
        }
    }

}
