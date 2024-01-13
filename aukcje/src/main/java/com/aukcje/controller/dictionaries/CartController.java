package com.aukcje.controller.dictionaries;

import com.aukcje.dto.AddressDTO;
import com.aukcje.dto.CartOfferDTO;
import com.aukcje.service.iface.AddressService;
import com.aukcje.service.iface.CartOfferService;
import com.aukcje.service.iface.UserService;
import com.aukcje.service.iface.UserStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.inject.Inject;
import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor(onConstructor = @__(@Inject))
@RequestMapping("/koszyk")
public class CartController {

    private final CartOfferService cartOfferService;
    private final UserService userService;
    private final UserStatusService userStatusService;
    private final AddressService addressService;

    @GetMapping("")
    public String getCart(Principal principal, Model model){
        Long userId = userService.findByUsername(principal.getName()).getId();
        List<CartOfferDTO> cartOfferDTOS = cartOfferService.getAll(userId);
        List<AddressDTO> addressDTOS = addressService.findNotDeletedByUserId(userId);

        double fullPrice = 0.0;

        for(CartOfferDTO cartOfferDTO : cartOfferDTOS){
            fullPrice += (cartOfferDTO.getOffer().getPrice() * cartOfferDTO.getQuantity());
        }

        model.addAttribute("cartOfferDTOS", cartOfferDTOS);
        model.addAttribute("allItemsPrice", fullPrice);
        model.addAttribute("addressDTOS", addressDTOS);

        return "/views/user/cart/cart";
    }

}
