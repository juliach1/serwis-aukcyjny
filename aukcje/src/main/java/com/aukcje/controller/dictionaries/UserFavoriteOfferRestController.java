package com.aukcje.controller.dictionaries;

import com.aukcje.dto.UserDTO;
import com.aukcje.dto.UserFavoriteOfferDTO;
import com.aukcje.enums.UserStatusEnum;
import com.aukcje.exception.customException.OfferNotFoundException;
import com.aukcje.exception.customException.UserNotFoundException;
import com.aukcje.service.iface.UserFavoriteOfferService;
import com.aukcje.service.iface.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.security.Principal;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Inject))
@RequestMapping("/ulubione")
public class UserFavoriteOfferRestController {

    private final UserFavoriteOfferService userFavoriteOfferService;
    private final UserService userService;

    @GetMapping("/dodaj")
    public void addOfferToFavorites(
            Principal principal,
            @RequestParam(value = "ofertaId") Long offerId
    ) throws UserNotFoundException, OfferNotFoundException {

        UserDTO userDTO = userService.findByUsername(principal.getName());
        if (userDTO.isActive()){
            System.out.println("DODAWANIE DO ULUBIONYCH Z KOSZYKA");
            userFavoriteOfferService.add(offerId, userDTO.getId());
        }
    }

    @GetMapping("/usun")
    public void removeOfferFromFavorites(@RequestParam(value = "ofertaId") Long offerId){
        userFavoriteOfferService.deleteAllByOfferId(offerId);
    }

    @GetMapping("/zmien")
    public void offerToFavorites(
            Principal principal,
            @RequestParam(value = "ofertaId") Long offerId
    ) throws UserNotFoundException, OfferNotFoundException {

        UserDTO userDTO = userService.findByUsername(principal.getName());
        if (userDTO.isActive()) {
            userFavoriteOfferService.toggle(offerId, userDTO.getId());
        }
    }

}
