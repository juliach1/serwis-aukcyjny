package com.aukcje.controller.dictionaries;

import com.aukcje.dto.OfferDTO;
import com.aukcje.dto.UserDTO;
import com.aukcje.dto.UserStatusDTO;
import com.aukcje.entity.Offer;
import com.aukcje.exception.customException.IncorrectUserStatusException;
import com.aukcje.exception.customException.OfferStatusNotFoundException;
import com.aukcje.exception.customException.UserNotFoundException;
import com.aukcje.model.UserEditModel;
import com.aukcje.model.UserSearchModel;
import com.aukcje.service.iface.OfferService;
import com.aukcje.service.iface.UserRatingService;
import com.aukcje.service.iface.UserService;
import com.aukcje.service.iface.UserStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Objects;

@Controller
@RequiredArgsConstructor(onConstructor = @__(@Inject))
@RequestMapping("/uzytkownik")
public class UserController {

    private final UserService userService;
    private final OfferService offerService;
    private final UserRatingService userRatingService;

    private final Integer PAGE_SIZE = 12;

    @GetMapping("/podglad/{uzytkownikId}")
    public String userPage(@PathVariable("uzytkownikId") Long userId,
                           Principal principal,
                           Model model) throws UserNotFoundException, OfferStatusNotFoundException {

        UserDTO principalDTO = userService.findByUsername(principal.getName());
        UserDTO userDTO = userService.findById(userId);
        Boolean isUserPrincipal = userDTO.getId() == principalDTO.getId();

        List<OfferDTO> actionDTOS = offerService.findActiveAuctionsByUserId(userId, PAGE_SIZE);
        List<OfferDTO> buyNowDTOS = offerService.findActiveBuyNowByUserId(userId, PAGE_SIZE);

        Integer offersNumber = offerService.getActiveOffersNumberByUserId(userId);
        Long ratingsNumber = userRatingService.getRatingsNumberByUserId(userId);

        model.addAttribute("userDTO", userDTO);
        model.addAttribute("auctionDTOS", actionDTOS);
        model.addAttribute("buyNowDTOS", buyNowDTOS);
        model.addAttribute("isPrincipalProfile", isUserPrincipal);
        model.addAttribute("offersNumber", offersNumber);
        model.addAttribute("ratingsNumber", ratingsNumber);

        System.out.println("Controller podglądu dla usera "+userDTO.getId());
        return "/views/user/user/userprofile";
    }
}
