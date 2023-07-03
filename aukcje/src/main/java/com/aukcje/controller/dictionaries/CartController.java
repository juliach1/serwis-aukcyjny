package com.aukcje.controller.dictionaries;

import com.aukcje.dto.CartOfferDTO;
import com.aukcje.service.iface.CartOfferService;
import com.aukcje.service.iface.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/uzytkownik/koszyk")
public class CartController {

    @Autowired
    CartOfferService cartOfferService;

    @Autowired
    UserService userService;

    @GetMapping("")
    public String getCart(Principal principal, Model model){

        Long userId = userService.findByUsername(principal.getName()).getId();

        List<CartOfferDTO> cartOfferDTOS = cartOfferService.getAll(userId);

//        model.addAttribute("cartOfferDTOS", cartOfferDTOS);

        for(CartOfferDTO cartOffer : cartOfferDTOS){
            System.out.println("CART OFFER DTOS:");
            System.out.println("offer: " + cartOffer.getOfferDTO().id);
            System.out.println("user: " + cartOffer.getUserDTO().id);
        }

        return "";
    }



}
