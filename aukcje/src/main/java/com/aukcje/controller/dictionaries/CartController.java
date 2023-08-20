package com.aukcje.controller.dictionaries;

import com.aukcje.dto.CartOfferDTO;
import com.aukcje.dto.CategoryDTO;
import com.aukcje.dto.UserDTO;
import com.aukcje.exception.NoSuchCategoryException;
import com.aukcje.exception.customException.CartOfferNotFoundException;
import com.aukcje.service.iface.CartOfferService;
import com.aukcje.service.iface.UserService;
import com.aukcje.service.iface.UserStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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

        double fullPrice = 0.0;

        for(CartOfferDTO cartOfferDTO : cartOfferDTOS){
            fullPrice += (cartOfferDTO.getOffer().getPrice() * cartOfferDTO.getQuantity());
        }

        model.addAttribute("cartOfferDTOS", cartOfferDTOS);
        model.addAttribute("allItemsPrice", fullPrice);

        return "/views/user/cart/cart";
    }

    @GetMapping("/usun/{pozycjaKoszykaId}")
    public String deleteCategory(Principal principal,
                                 @PathVariable("pozycjaKoszykaId") Long cartOfferId) throws CartOfferNotFoundException {

        UserDTO userDTO = userService.findByUsername(principal.getName());

        if( cartOfferService.isCartOfferAssignedToUser(userDTO.getId(), cartOfferId)){
            cartOfferService.delete(cartOfferId);
        }else {
            throw new CartOfferNotFoundException();
        }

        return "redirect:/koszyk";
    }

}
