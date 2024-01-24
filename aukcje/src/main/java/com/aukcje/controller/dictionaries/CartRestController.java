package com.aukcje.controller.dictionaries;

import com.aukcje.dto.UserDTO;
import com.aukcje.exception.customException.CartOfferNotFoundException;
import com.aukcje.service.iface.CartOfferService;
import com.aukcje.service.iface.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Inject))
@RequestMapping("/koszyk")
public class CartRestController {

    private final CartOfferService cartOfferService;
    private final UserService userService;

    @GetMapping("/dodaj")
    public void addOfferToCart(HttpServletResponse response,
                                  Principal principal,
                                  @RequestParam(value = "ofertaId") Long offerId,
                                  @RequestParam(value = "szt", required = false) Integer pcs) {
        UserDTO user = userService.findByUsername(principal.getName());

        if(userService.isUserActive(user)) {

            if(pcs == null)
                pcs = 1;
            cartOfferService.add(user.getId(), offerId, pcs);
            response.setStatus(HttpServletResponse.SC_ACCEPTED);

        }else {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
    }

    @GetMapping("/usun")
    public void deleteCartOffer(Principal principal,
                                  @RequestParam(value = "ofertaKoszykaId") Long cartOfferId,
                                  @RequestParam(value = "szt", required = false) Integer pcs
    ) throws CartOfferNotFoundException {

        UserDTO principalDTO = userService.findByUsername(principal.getName());
        if(cartOfferService.isCartOfferAssignedToUser(principalDTO.getId(), cartOfferId)){
            if(pcs == null)
                cartOfferService.delete(cartOfferId);
            else{
                if(userService.isUserActive(principalDTO))
                    cartOfferService.changeQuantity(cartOfferId, pcs);
            }
        }else
            throw new CartOfferNotFoundException();
    }
}
