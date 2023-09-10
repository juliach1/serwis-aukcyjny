package com.aukcje.controller.dictionaries;

import com.aukcje.dto.OfferDTO;
import com.aukcje.dto.OfferSearchDTO;
import com.aukcje.dto.UserDTO;
import com.aukcje.model.OfferSearchModel;
import com.aukcje.service.iface.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/uzytkownik/strona-glowna")
public class MainPageController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private OfferService offerService;

    @Autowired
    private OfferTypeService offerTypeService;

    @Autowired
    private UserService userService;

    private final Integer PAGE_SIZE = 12;

    @GetMapping()
    public String showMainPage(Principal principal, Model model) {

        OfferSearchDTO offerSearchDTO = new OfferSearchDTO(offerTypeService,categoryService);
        UserDTO userDTO = userService.findByUsername( principal.getName());

        List<OfferDTO> favoriteDTOS = offerService.findFavoriteForUser(userDTO.getId(), PAGE_SIZE);
        List<OfferDTO> auctionDTOS = offerService.findNewAuctions(PAGE_SIZE);
        List<OfferDTO> buyNowDTOS = offerService.findNewBuyNow(PAGE_SIZE);

        offerService.setIsFavorite(auctionDTOS, userDTO.getId());
        offerService.setIsFavorite(buyNowDTOS, userDTO.getId());

        model.addAttribute("pageSize", PAGE_SIZE);

        model.addAttribute("offerSearchModel", new OfferSearchModel());
        model.addAttribute("offerSearchDTO", offerSearchDTO);

        model.addAttribute("favoriteDTOS", favoriteDTOS);
        model.addAttribute("auctionDTOS", auctionDTOS);
        model.addAttribute("buyNowDTOS", buyNowDTOS);

        return "/views/user/main/main-page";
    }

    @GetMapping("/szukaj/przetworz")
    public String searchOffer(Model model,
                              @Valid @ModelAttribute("offerSearchModel") OfferSearchModel offerSearchModel,
                              @RequestParam(value = "fraza", required = false) String phrase,
                              @RequestParam(value = "typyOfert", required = false) List<String> offerTypes,
                              @RequestParam(value = "kategoria", required = false) Integer searchCategory,
                              @RequestParam(value = "typSortowania", required = false) String sortType,
                              @RequestParam(value = "cenaMin", required = false) Double minPrice,
                              @RequestParam(value = "cenaMax", required = false) Double maxPrice,
                              @RequestParam(value = "szukacWOpisie", required = false) String searchInDesc){

        offerSearchModel.setMinSmallerThanMax(offerSearchModel.isMinSmallerThanMax());

        OfferSearchDTO offerSearchDTO = new OfferSearchDTO(offerTypeService,categoryService);

        List<OfferDTO> offers = offerService.findByOfferSearchModel(offerSearchModel);

        model.addAttribute("offers", offers);
        model.addAttribute("offerSearchModel", offerSearchModel);
        model.addAttribute("pageSize", PAGE_SIZE);
        model.addAttribute("offerSearchDTO", offerSearchDTO);

        return "/views/user/main/search-offer";
    }

}
