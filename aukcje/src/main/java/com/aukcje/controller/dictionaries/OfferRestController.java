package com.aukcje.controller.dictionaries;

import com.aukcje.dto.UserDTO;
import com.aukcje.exception.customException.OfferDeletePermissionDeniedException;
import com.aukcje.service.iface.OfferService;
import com.aukcje.service.iface.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.security.Principal;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Inject))
@RequestMapping("/oferta")
public class OfferRestController {

    private final UserService userService;
    private final OfferService offerService;

    @GetMapping("/usun/{ofertaId}")
    public void deleteOffer(
            @PathVariable("ofertaId") Long offerId,
            Principal principal
    ) throws OfferDeletePermissionDeniedException {
        UserDTO user = userService.findByUsername(principal.getName());

        if(offerService.isOfferAssignedToUser(user.getId(), offerId))
            offerService.delete(offerId);
        else
            throw new OfferDeletePermissionDeniedException();
    }

}
