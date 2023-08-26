package com.aukcje.controller.dictionaries;

import com.aukcje.dto.UserDTO;
import com.aukcje.enums.UserStatusEnum;
import com.aukcje.exception.customException.OfferNotFoundException;
import com.aukcje.exception.customException.UserNotFoundException;
import com.aukcje.service.iface.UserFavoriteOfferService;
import com.aukcje.service.iface.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/ulubione")
public class UserFavoriteOfferRestController {

    @Autowired
    private UserFavoriteOfferService userFavoriteOfferService;

    @Autowired
    private UserService userService;

    @GetMapping("/dodaj")
    public void addOfferToFavorites(
            Principal principal,
            @RequestParam(value = "ofertaId") Long offerId
    ) throws UserNotFoundException, OfferNotFoundException {

        UserDTO userDTO = userService.findByUsername(principal.getName());
        if (userDTO.getUserStatus().getName().equals(UserStatusEnum.AKTYWNY.toString())){
            userFavoriteOfferService.add(offerId, userDTO.getId());
        }
    }

    @GetMapping("/usun")
    public void removeOfferFromFavorites(
            Principal principal,
            @RequestParam(value = "ofertaId") Long offerId
    ) throws UserNotFoundException, OfferNotFoundException {
        UserDTO userDTO = userService.findByUsername(principal.getName());
        if (userDTO.getUserStatus().getName().equals(UserStatusEnum.AKTYWNY.toString())){
            userFavoriteOfferService.remove(offerId, userDTO.getId());
        }
    }
}
