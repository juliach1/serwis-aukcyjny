package com.aukcje.controller.dictionaries;

import com.aukcje.dto.UserDTO;
import com.aukcje.enums.UserStatusEnum;
import com.aukcje.service.iface.CartOfferService;
import com.aukcje.service.iface.UserService;
import com.aukcje.service.iface.UserStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
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
                                  @RequestParam(value = "ofertaId", required = false) Long offerId,
                                  @RequestParam(value = "szt", required = false) Integer pcs,
                                  Model model
    ){

        UserDTO user = userService.findByUsername(principal.getName());

        if( user.getUserStatus().getName().equals(UserStatusEnum.AKTYWNY.name()) ){
            cartOfferService.add(user.getId(), offerId, pcs);
            response.setStatus(HttpServletResponse.SC_ACCEPTED);

        }else{
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            //TODO : WYJĄTEK, nie można wykonać bo konto nieaktywne
        }

    }
}
