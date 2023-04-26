package com.aukcje.controller.dictionaries;

import com.aukcje.dto.OfferDTO;
import com.aukcje.dto.UserAuctionDTO;
import com.aukcje.model.OfferAddModel;
import com.aukcje.service.iface.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/oferta")
public class OfferController {

    @Autowired
    OfferService offerService;

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
    public String addAddress(Model model){
        model.addAttribute("offerAddModel", new OfferAddModel());
        model.addAttribute("offerTypeDTOS", offerTypeService.findAll());
        model.addAttribute("itemConditionDTOS", itemConditionService.findAll());

        return "/user/offer/offeradd";
    }

//    @PostMapping("/dodaj/przetworz")
//    public String addOfferProcess(Model model, Principal principal,
//                                  @Valid @ModelAttribute("offerAddModel") OfferAddModel offerAddModel,
//                                  BindingResult bindingResult, @RequestParam("file") MultipartFile file){
//
//        if(bindingResult.hasErrors()){
//            model.addAttribute("offerAddModel", offerAddModel);
//            model.addAttribute("offerTypeDTOS", offerTypeService.findAll());
//            model.addAttribute("itemConditionDTOS", itemConditionService.findAll());
//
//            return "/user/offer/offeradd";
//        }
//        offerService.save(offerAddModel, userService.findByUsername(principal.getName()).getId(), file);
//
//    }


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


}
