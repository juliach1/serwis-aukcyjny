package com.aukcje.controller.dictionaries;

import com.aukcje.dto.OfferDTO;
import com.aukcje.dto.UserAuctionDTO;
import com.aukcje.dto.UserDTO;
import com.aukcje.exception.customException.*;
import com.aukcje.model.OfferAddModel;
import com.aukcje.service.iface.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static java.time.temporal.ChronoUnit.DAYS;

@Controller
@RequiredArgsConstructor(onConstructor = @__(@Inject))
@RequestMapping("/oferta")
public class OfferController {

    private final OfferService offerService;
    private final CategoryService categoryService;
    private final UserAuctionService userAuctionService;
    private final OfferTypeService offerTypeService;
    private final ItemConditionService itemConditionService;
    private final UserService userService;
    private final Integer PAGE_SIZE = 64;

    @GetMapping("/moje-oferty")
    public String myOffers(Principal principal, Model model) throws OfferStatusNotFoundException {
        UserDTO user = userService.findByUsername(principal.getName());
        List<OfferDTO> userOffers = offerService.findActiveByUserId(user.getId(), PAGE_SIZE);

        model.addAttribute("offerDTOS", userOffers);

        return "/views/user/offer/my-offers";
    }

    @GetMapping("/oferty-uzytkownika/{uzytkownikId}")
    public String userOffers(HttpServletRequest request,
                             @PathVariable("uzytkownikId") Long userId,
                             @RequestParam("isAuction") Boolean isTypeAuctions,
                             Model model) throws UserNotFoundException, OfferStatusNotFoundException {

        UserDTO userDTO = userService.findById(userId);

        //TODO: PAGINACJA ALBO ZWYKŁA LISTA!
        List<OfferDTO> userOffers;
        if(isTypeAuctions)
            userOffers = offerService.findActiveAuctionsByUserId(userId, PAGE_SIZE);
        else
            userOffers = offerService.findActiveBuyNowByUserId(userId, PAGE_SIZE);

        model.addAttribute("offerDTOS", userOffers);
        model.addAttribute("userDTO", userDTO);
        model.addAttribute("isAuction", isTypeAuctions);

        return "/views/user/offer/user-offers";
    }

    @GetMapping("/podglad/{ofertaId}")
    public String getOffer(@PathVariable("ofertaId") Long offerId, Principal principal, Model model) throws InactiveOfferException, OfferNotFoundException, OfferStatusNotFoundException {

        //TODO dodać: ograniczenie dla AKTYWNYCH; przy nieaktywnych (sprzedanych, usunietych) - blad
        UserDTO principalDTO = userService.findByUsername(principal.getName());
        OfferDTO offerDTO = offerService.findById(offerId);

        Boolean isSellerPricipal = principalDTO.getId() == offerDTO.getUser().getId();

        if(!offerService.isOfferActive(offerDTO))
            throw new InactiveOfferException();

        if (offerDTO.getEndDate() != null)
            offerDTO.setDaysLeft((int) DAYS.between(LocalDateTime.now(), offerDTO.getEndDate()));

        if (offerService.isOfferTypeAuction(offerDTO)) {
            model.addAttribute("startValue", offerDTO.getPrice());

            if (userAuctionService.hasUserAnyAuctions(offerDTO)) {
                UserAuctionDTO userAuctionDTO = userAuctionService.findOneWithHighestValue(offerDTO.getId());
                model.addAttribute("highestValueUserAuction", userAuctionDTO);
            }
        }
        model.addAttribute("offerDTO", offerDTO);
        model.addAttribute("isSellerPrincipal", isSellerPricipal);

        return "/views/user/offer/offer";
    }

    @GetMapping("/dodaj")
    public String addOffer(Model model, Principal principal) {
        //TODO dodać: tylko dla zwykłych userów
        model.addAttribute("offerAddModel", new OfferAddModel());
        model.addAttribute("offerTypeDTOS", offerTypeService.findAll());
        model.addAttribute("itemConditionDTOS", itemConditionService.findAll());

        return "/views/user/offer/offeradd";
    }

    @PostMapping("/dodaj/przetworz")
    public String addOfferProcess(Model model, Principal principal,
                                  @Valid @ModelAttribute("offerAddModel") OfferAddModel offerAddModel,
                                  BindingResult bindingResult, @RequestParam("file") MultipartFile file) {

        if (bindingResult.hasErrors() || !categoryService.isChosenCategoryForOfferAddCorrect(offerAddModel)) {
            model.addAttribute("offerAddModel", offerAddModel);
            model.addAttribute("offerTypeDTOS", offerTypeService.findAll());
            model.addAttribute("itemConditionDTOS", itemConditionService.findAll());
            model.addAttribute("isBaseCategoryChosen", offerAddModel.getIsBaseCategoryChosen());

            if (Objects.nonNull(offerAddModel.getCategoryId()))
                model.addAttribute("categoryId", offerAddModel.getCategoryId());


            if (!categoryService.isChosenCategoryForOfferAddCorrect(offerAddModel)) {
                //TODO poprawić: ZEBY PO USTAWIENIU POPRAWNEJ KATEGORII BŁĄD ZNIKAŁ!
                bindingResult.rejectValue("categoryId", "error.badCategory", "Wybierz kategorię");
            }

            return "/views/user/offer/offeradd";
        }
        Long userId = userService.findByUsername(principal.getName()).getId();
        Long newOfferId = offerService.save(offerAddModel, userId, file);

        return "redirect:/oferta/podglad/" + newOfferId;

    }


    @GetMapping("/edytuj/{ofertaId}")
    public String editOffer(Principal principal,
                            @PathVariable("ofertaId") Long offerId,
                            Model model) throws OfferEditPermissionDeniedException, OfferNotFoundException, OfferStatusNotFoundException {

        Long userId = userService.findByUsername(principal.getName()).getId();
        OfferAddModel offerAddModel = new OfferAddModel();
        offerAddModel.setId(offerId);

        if (offerService.isOfferAssignedToUser(userId, offerId)) {
            OfferDTO offerDTO = offerService.findById(offerId);
            model.addAttribute("offerAddModel", offerAddModel);
            model.addAttribute("offerTypeDTOS", offerTypeService.findAll());
            model.addAttribute("itemConditionDTOS", itemConditionService.findAll());
            model.addAttribute("offerDTO", offerDTO);

            return "/views/user/offer/offeredit";
        }else{
            throw new OfferEditPermissionDeniedException();
        }
    }

    @PostMapping("/edytuj/przetworz")
    public String editOfferProcess(Principal principal,
                                   Model model,
                                   @Valid @ModelAttribute("offerAddModel") OfferAddModel offerModel,
                                   @RequestParam("file") MultipartFile file,
                                   BindingResult bindingResult) {


        if (bindingResult.hasErrors() || !categoryService.isChosenCategoryForOfferAddCorrect(offerModel)) {
            model.addAttribute("offerAddModel", offerModel);
            model.addAttribute("offerTypeDTOS", offerTypeService.findAll());
            model.addAttribute("itemConditionDTOS", itemConditionService.findAll());
            model.addAttribute("isBaseCategoryChosen", offerModel.getIsBaseCategoryChosen());

            if (Objects.nonNull(offerModel.getCategoryId()))
                model.addAttribute("categoryId", offerModel.getCategoryId());

            if (!categoryService.isChosenCategoryForOfferAddCorrect(offerModel)) {
                //TODO poprawić: ZEBY PO USTAWIENIU POPRAWNEJ KATEGORII BŁĄD ZNIKAŁ!
                bindingResult.rejectValue("categoryId", "error.badCategory", "Wybierz kategorię");
            }
            return "/views/user/offer/offeredit";
        }
        Long userId = userService.findByUsername(principal.getName()).getId();
        Long newOfferId = offerService.save(offerModel, userId, file);

        return "redirect:/oferta/podglad/" + newOfferId;
    }
}