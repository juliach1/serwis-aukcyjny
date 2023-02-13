package com.aukcje.controller.dictionaries;

import com.aukcje.dto.CategoryDTO;
import com.aukcje.dto.CategorySelectDTO;
import com.aukcje.dto.OfferDTO;
import com.aukcje.dto.OfferSearchDTO;
import com.aukcje.entity.Offer;
import com.aukcje.model.OfferSearchModel;
import com.aukcje.model.UserEditModel;
import com.aukcje.model.UserSearchModel;
import com.aukcje.service.iface.CategoryService;
import com.aukcje.service.iface.OfferService;
import com.aukcje.service.iface.OfferTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
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

    private final Integer PAGE_SIZE = 12;

    @GetMapping("")
    public String showMainPage(Model model) {

        OfferSearchDTO offerSearchDTO = new OfferSearchDTO(offerTypeService,categoryService);

        model.addAttribute("pageSize", PAGE_SIZE);

        model.addAttribute("offerSearchModel", new OfferSearchModel());
        model.addAttribute("offerSearchDTO", offerSearchDTO);

        model.addAttribute("auctionDTOS", offerService.findNewAuctions(PAGE_SIZE));
        model.addAttribute("buyNowDTOS", offerService.findNewBuyNow(PAGE_SIZE));

        return "/user/main/main-page";
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

        return "/user/main/search-offer";
    }

}
