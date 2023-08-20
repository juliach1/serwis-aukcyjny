package com.aukcje.controller.dictionaries;

import com.aukcje.dto.UserDTO;
import com.aukcje.enums.UserStatusEnum;
import com.aukcje.exception.customException.CartOfferNotFoundException;
import com.aukcje.service.iface.CartOfferService;
import com.aukcje.service.iface.UserService;
import com.aukcje.service.iface.UserStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

@RestController
@RequestMapping("/koszyk")
public class CartRestController {

    @Autowired
    CartOfferService cartOfferService;

    @Autowired
    UserService userService;

    @Autowired
    UserStatusService userStatusService;

    @GetMapping("/dodaj")
    public void addOfferToCart(HttpServletResponse response,
                                  Principal principal,
                                  @RequestParam(value = "ofertaId") Long offerId,
                                  @RequestParam(value = "szt", required = false) Integer pcs,
                                  Model model
    ){

        UserDTO user = userService.findByUsername(principal.getName());

        if( user.getUserStatus().getName().equals(UserStatusEnum.AKTYWNY.name()) ){
            if(pcs == null) pcs = 1;
            System.out.println("dodawanie do koszyka");
            cartOfferService.add(user.getId(), offerId, pcs);
            response.setStatus(HttpServletResponse.SC_ACCEPTED);

        }else{
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
    }


    @GetMapping("/usun")
    public void deleteCartOffer(Principal principal,
                                  @RequestParam(value = "ofertaKoszykaId") Long cartOfferId,
                                  @RequestParam(value = "szt", required = false) Integer pcs
    ) throws CartOfferNotFoundException {

        UserDTO user = userService.findByUsername(principal.getName());
        if(cartOfferService.isCartOfferAssignedToUser(user.id, cartOfferId)){
            if(pcs == null){
                System.out.println("USUWANIE WSZYSTKICH");
                cartOfferService.delete(cartOfferId);   //usuń wszystkie dla danej oferty
            }else{        //usuń jedną
                if( user.getUserStatus().getName().equals(UserStatusEnum.AKTYWNY.name()) ){
                    System.out.println("USUWANIE JEDNEGO");
                    cartOfferService.changeQuantity(cartOfferId, pcs);
                }
            }
        }else throw new CartOfferNotFoundException();
    }
}
