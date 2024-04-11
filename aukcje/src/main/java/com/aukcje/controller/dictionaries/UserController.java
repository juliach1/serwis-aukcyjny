package com.aukcje.controller.dictionaries;

import com.aukcje.dto.OfferDTO;
import com.aukcje.dto.UserDTO;
import com.aukcje.exception.customException.OfferStatusNotFoundException;
import com.aukcje.exception.customException.UserNotFoundException;
import com.aukcje.service.iface.OfferService;
import com.aukcje.service.iface.UserRatingService;
import com.aukcje.service.iface.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor(onConstructor = @__(@Inject))
@RequestMapping("/uzytkownik")
public class UserController {

    private final UserService userService;
    private final OfferService offerService;
    private final UserRatingService userRatingService;
    private final Integer PAGE_SIZE = 12;

    @PostMapping("/zmien-zdjecie")
    public String changeProfilePhoto(
                           Principal principal,
                           @RequestParam("file") MultipartFile file) throws UserNotFoundException {
        UserDTO userDTO = userService.findByUsername(principal.getName());
        long userId = userDTO.getId();
        userService.updateProfilePhoto(userId, file);

        return "redirect:/uzytkownik/podglad/"+userId;
    }
    @GetMapping("/podglad/{uzytkownikId}")
    public String userPage(@PathVariable("uzytkownikId") Long userId,
                           Principal principal,
                           Model model) throws UserNotFoundException, OfferStatusNotFoundException {
        UserDTO userDTO = userService.findById(userId);
        createModel(model, principal, userDTO);

        return "/views/user/user/user-profile";
    }
    @GetMapping("/moj-profil")
    public String userPage( Principal principal,
                           Model model) throws OfferStatusNotFoundException {
        UserDTO userDTO = userService.findByUsername(principal.getName());
        createModel(model, principal, userDTO);

        return "/views/user/user/user-profile";
    }

    private void createModel(Model model, Principal principal, UserDTO userDTO) throws OfferStatusNotFoundException {
        UserDTO principalDTO = userService.findByUsername(principal.getName());
        Long userId = userDTO.getId();
        List<OfferDTO> actionDTOS = offerService.findActiveAuctionsByUserId(userId, PAGE_SIZE);
        List<OfferDTO> buyNowDTOS = offerService.findActiveBuyNowByUserId(userId, PAGE_SIZE);
        Integer offersNumber = offerService.getActiveOffersNumberByUserId(userId);
        Long ratingsNumber = userRatingService.getRatingsNumberByUserId(userId);

        Boolean isUserPrincipal = userDTO.getId() == principalDTO.getId();

        model.addAttribute("userDTO", userDTO);
        model.addAttribute("auctionDTOS", actionDTOS);
        model.addAttribute("buyNowDTOS", buyNowDTOS);
        model.addAttribute("isPrincipalProfile", isUserPrincipal);
        model.addAttribute("offersNumber", offersNumber);
        model.addAttribute("ratingsNumber", ratingsNumber);

    }
}
