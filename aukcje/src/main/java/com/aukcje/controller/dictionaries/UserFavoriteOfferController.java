package com.aukcje.controller.dictionaries;

import com.aukcje.dto.OfferDTO;
import com.aukcje.dto.OfferSearchDTO;
import com.aukcje.dto.UserDTO;
import com.aukcje.model.OfferSearchModel;
import com.aukcje.service.iface.CategoryService;
import com.aukcje.service.iface.OfferService;
import com.aukcje.service.iface.OfferTypeService;
import com.aukcje.service.iface.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/uzytkownik/ulubione")
public class UserFavoriteOfferController {

    private final Integer PAGE_SIZE = 24;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private OfferService offerService;

    @Autowired
    private OfferTypeService offerTypeService;

    @Autowired
    private UserService userService;


    @GetMapping()
    public String showFavoritesPage(Principal principal, Model model) {

        OfferSearchDTO offerSearchDTO = new OfferSearchDTO(offerTypeService,categoryService);
        UserDTO userDTO = userService.findByUsername( principal.getName());

        List<OfferDTO> favoriteDTOS = offerService.findFavoriteForUser(userDTO.getId(), PAGE_SIZE);

        model.addAttribute("pageSize", PAGE_SIZE);

        model.addAttribute("offerSearchModel", new OfferSearchModel());
        model.addAttribute("offerSearchDTO", offerSearchDTO);

        model.addAttribute("favoriteDTOS", favoriteDTOS);

        return "/views/user/offer/favorite-offers";
    }
}
