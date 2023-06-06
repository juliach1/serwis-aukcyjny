package com.aukcje.controller.dictionaries;

import com.aukcje.dto.OfferDTO;
import com.aukcje.dto.UserAuctionDTO;
import com.aukcje.model.AddressModel;
import com.aukcje.model.CategoryModel;
import com.aukcje.model.OfferAddModel;
import com.aukcje.service.iface.*;
import com.aukcje.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Objects;

import static java.time.temporal.ChronoUnit.DAYS;

@Controller
@RequestMapping("/oferta")
public class OfferController {

    @Autowired
    OfferService offerService;

    @Autowired
    CategoryService categoryService;
    @Autowired
    UserAuctionService userAuctionService;

    @Autowired
    OfferTypeService offerTypeService;

    @Autowired
    ItemConditionService itemConditionService;

    @Autowired
    UserService userService;

    @GetMapping("/podglad/{ofertaId}")
    public String getOffer(@PathVariable("ofertaId") Long offerId, Model model){

        //TODO dodac ograniczenie dla AKTYWNYCH
        //przy nieaktywnych (sprzedanych, usunietych) - blad
        OfferDTO offerDTO =  offerService.findById(offerId);
        offerDTO.setDaysLeft((int) DAYS.between(LocalDateTime.now(), offerDTO.getEndDate()));

        if( offerService.isOfferTypeAuction(offerDTO) ){
            model.addAttribute("startValue", offerDTO.getPrice());

            if(!userAuctionService.findAllByOfferId(offerDTO.getId()).isEmpty()){
                UserAuctionDTO userAuctionDTO = userAuctionService.findOneWithHighestValue(offerDTO.getId());
                System.out.println("USER-AUCTION:"+userAuctionDTO);
                model.addAttribute("highestValueUserAuction", userAuctionDTO);
            }
        }

        model.addAttribute("offerDTO", offerDTO);

        return "/user/offer/offer";
    }

    @GetMapping("/dodaj")
    public String addOffer(Model model){
        model.addAttribute("offerAddModel", new OfferAddModel());
        model.addAttribute("offerTypeDTOS", offerTypeService.findAll());
        model.addAttribute("itemConditionDTOS", itemConditionService.findAll());

        return "/user/offer/offeradd";
    }

    @PostMapping("/dodaj/przetworz")
    public String addOfferProcess(Model model, Principal principal,
                                  @Valid @ModelAttribute("offerAddModel") OfferAddModel offerAddModel,
                                  BindingResult bindingResult, @RequestParam("file") MultipartFile file,
                                  Errors error) {

        if (bindingResult.hasErrors() || !categoryService.isChosenCategoryForOfferAddCorrect(offerAddModel) ) {
            model.addAttribute("offerAddModel", offerAddModel);
            model.addAttribute("offerTypeDTOS", offerTypeService.findAll());
            model.addAttribute("itemConditionDTOS", itemConditionService.findAll());
            model.addAttribute("isBaseCategoryChosen", offerAddModel.getIsBaseCategoryChosen());

            if(Objects.nonNull(offerAddModel.getCategoryId())) {
                model.addAttribute("categoryId", offerAddModel.getCategoryId());
            }

            if (!categoryService.isChosenCategoryForOfferAddCorrect(offerAddModel)) {
                System.out.println("BAZOWA KATEGORIA: " + offerAddModel.getIsBaseCategoryChosen());

                //TODO POPRAWIC ZEBY PO USTAWIENIU POPRAWNEJ KATEGORII BŁĄD ZNIKAŁ!
                bindingResult.rejectValue("categoryId", "error.badCategory", "Wybierz kategorię");
            }

            return "/user/offer/offeradd";
        }


        Long userId = userService.findByUsername(principal.getName()).getId();

        Long newOfferId = offerService.save(offerAddModel, userId, file);

        return "redirect:/oferta/podglad/"+newOfferId;

    }


    @GetMapping("/edytuj/{ofertaId}")
    public String editOffer(Principal principal,
                              @PathVariable("ofertaId") Long offerId,
                            Model model){

        Long userId = userService.findByUsername(principal.getName()).getId();

        System.out.println("EDYCJA "+offerId);

        if( offerService.isOfferAssignedToUser(userId, offerId) ){
            OfferDTO offerDTO = offerService.findById(offerId);

            model.addAttribute("offerAddModel", new OfferAddModel());
            model.addAttribute("offerTypeDTOS", offerTypeService.findAll());
            model.addAttribute("itemConditionDTOS", itemConditionService.findAll());
            model.addAttribute("offerDTO", offerDTO);

            return "/user/offer/offeredit";
        }

        return "redirect:/oferta/dodaj";
    }

    @PostMapping("/edytuj/przetworz")
    public String editOfferProcess( Principal principal,
                                      Model model,
                                      @Valid @ModelAttribute("offerAddModel") OfferAddModel offerModel,
                                        @RequestParam("file") MultipartFile file,
                                      BindingResult bindingResult ){


        if (bindingResult.hasErrors() || !categoryService.isChosenCategoryForOfferAddCorrect(offerModel) ) {
            model.addAttribute("offerAddModel", offerModel);
            model.addAttribute("offerTypeDTOS", offerTypeService.findAll());
            model.addAttribute("itemConditionDTOS", itemConditionService.findAll());
            model.addAttribute("isBaseCategoryChosen", offerModel.getIsBaseCategoryChosen());

            if(Objects.nonNull(offerModel.getCategoryId())) {
                model.addAttribute("categoryId", offerModel.getCategoryId());
            }

            if (!categoryService.isChosenCategoryForOfferAddCorrect(offerModel)) {
                //TODO POPRAWIC ZEBY PO USTAWIENIU POPRAWNEJ KATEGORII BŁĄD ZNIKAŁ!
                bindingResult.rejectValue("categoryId", "error.badCategory", "Wybierz kategorię");
            }

            return "/user/offer/offeredit";
        }

        Long userId = userService.findByUsername(principal.getName()).getId();

        Long newOfferId = offerService.save(offerModel, userId, file);

        return "redirect:/oferta/podglad/"+newOfferId;

    }

//
//    @PostMapping("/edytuj/przetworz")
//    public String editAddressProcess( Principal principal,
//                                      Model model,
//                                      @Valid @ModelAttribute("addressModel") AddressModel addressModel,
//                                      BindingResult bindingResult ){
//        if(bindingResult.hasErrors()){
//            model.addAttribute("countries", countryService.findAll());
//            model.addAttribute("addressModel", addressModel);
//
//            return "/user/address/addressedit";
//        }
//
//        Long userId = userService.findByUsername(principal.getName()).getId();
//        addressService.updateAddress(addressModel, userId);
//        return "redirect:/uzytkownik/adres/dodaj";
//    }
//}

//    @PostMapping("/dodaj/przetworz")
//    public String addOfferProcess(Model model, Principal principal,
//                                  @Valid @ModelAttribute("offerAddModel") OfferAddModel offerAddModel,
//                                  BindingResult bindingResult, @RequestParam("file") MultipartFile file,
//                                  Errors error) {
//
//        if (bindingResult.hasErrors()) {
//            model.addAttribute("offerAddModel", offerAddModel);
//            model.addAttribute("offerTypeDTOS", offerTypeService.findAll());
//            model.addAttribute("itemConditionDTOS", itemConditionService.findAll());
//
//            if(Objects.nonNull(offerAddModel.getCategoryId())) {
//                model.addAttribute("categoryId", offerAddModel.getCategoryId());
//            }
//
//            if (!categoryService.isChosenCategoryForOfferAddCorrect(offerAddModel)) {
//                model.addAttribute("badCategory", "Wybierz kategorię");
//            }
//            return "/user/offer/offeradd";
//        }else {
//            model.addAttribute("badCategory", "");
//        }
//
//            Long userId = userService.findByUsername(principal.getName()).getId();
//
//            offerService.save(offerAddModel, userId, file);
//
//            return "/user/offer/offeradd";
//
//    }

}
//    @PostMapping("/dodaj-oferte-przetwarzanie")
//    public String addOfferProcessing(Model model, Principal principal,
//                                     @Valid @ModelAttribute("editNieruchomoscModel") AddNieruchomoscModelByEmployee nieruchomoscModel,
//                                     BindingResult bindingResult, @RequestParam("file") MultipartFile file) throws EditOfferWrongDataException, RodzajNieruchomosciNotFoundException,
//            IncorrectTypNieruchomosciException, IncorrectOgrzewanieException {
//        if(bindingResult.hasErrors()){
//            model.addAttribute("rodzajeNieruchomosci", rodzajNieruchomosciService.findAll());
//            model.addAttribute("ogrzewanie", ogrzewanieService.findAll());
//            model.addAttribute("nieruchomoscModel", nieruchomoscModel);
//            model.addAttribute("typyNieruchomosci", typNieruchomosciRepository.findAll());
//            return "employee/employee_add_offer";
//        }
//        nieruchomoscService.addNieruchomoscByAddNieruchomoscModel(nieruchomoscModel,
//                pracownikService.findByUserName(principal.getName()).getId(), file);
//
//        return "redirect:/pracownik/twoje-oferty?dodawanie-oferty-udane";
//    }

