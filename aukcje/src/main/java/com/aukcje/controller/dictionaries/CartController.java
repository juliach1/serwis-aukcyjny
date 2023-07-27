package com.aukcje.controller.dictionaries;

import com.aukcje.dto.CartOfferDTO;
import com.aukcje.dto.UserDTO;
import com.aukcje.enums.UserStatusEnum;
import com.aukcje.model.AddressModel;
import com.aukcje.service.iface.CartOfferService;
import com.aukcje.service.iface.UserService;
import com.aukcje.service.iface.UserStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/koszyk")
public class CartController {

    @Autowired
    CartOfferService cartOfferService;

    @Autowired
    UserService userService;

    @Autowired
    UserStatusService userStatusService;


    @GetMapping("")
    public String getCart(Principal principal, Model model){

        Long userId = userService.findByUsername(principal.getName()).getId();

        List<CartOfferDTO> cartOfferDTOS = cartOfferService.getAll(userId);

//        model.addAttribute("cartOfferDTOS", cartOfferDTOS);

        for(CartOfferDTO cartOffer : cartOfferDTOS){
            System.out.println("CART OFFER DTOS:");
            System.out.println("offer: " + cartOffer.getOffer().id);
            System.out.println("user: " + cartOffer.getUser().id);
        }

        return "";
    }


    //TODO: POMOCY!! jak mam obsłużyć geta z parametrami, jak je przekazać bez form, jak rzyć
    @GetMapping("/dodaj")
    public String addOfferToCart(Principal principal,
                                 @RequestParam(value = "ofertaId", required = false) Long offerId,
                                 @RequestParam(value = "szt", required = false) Integer pcs,
                                 BindingResult bindingResult){

        UserDTO user = userService.findByUsername(principal.getName());

        if( user.getUserStatus().getName().equals(UserStatusEnum.AKTYWNY.name()) ){
            cartOfferService.add(user.getId(), offerId, pcs);
        }else{
            //TODO : WYJĄTEK, nie można wykonać bo konto nieaktywne
        }
        return "";

    }

}
