package com.aukcje.controller.dictionaries;

import com.aukcje.dto.OfferDTO;
import com.aukcje.dto.UserDTO;
import com.aukcje.model.OfferAddModel;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Objects;

@RequestMapping("/zakup")
public class PurchaseController {
//
//    @PostMapping("/przetworz")
//    public String newPurchase(Principal principal, Model model){
//
//        UserDTO user = userService.findByUsername(principal.getName());
//        List<OfferDTO> userOffers = offerService.findByUserId(user.getId());
//
//        model.addAttribute("offerDTOS", userOffers);
//
//        return "/views/user/offer/user-offers";
//
//    }

//    @PostMapping("/dodaj/przetworz")
//    public String addOfferProcess(Model model, Principal principal,
//                                  @Valid @ModelAttribute("offerAddModel") OfferAddModel offerAddModel,
//                                  BindingResult bindingResult, @RequestParam("file") MultipartFile file,
//                                  Errors error) {
//
//        if (bindingResult.hasErrors() || !categoryService.isChosenCategoryForOfferAddCorrect(offerAddModel)) {
//            model.addAttribute("offerAddModel", offerAddModel);
//            model.addAttribute("offerTypeDTOS", offerTypeService.findAll());
//            model.addAttribute("itemConditionDTOS", itemConditionService.findAll());
//            model.addAttribute("isBaseCategoryChosen", offerAddModel.getIsBaseCategoryChosen());
//
//            if (Objects.nonNull(offerAddModel.getCategoryId())) {
//                model.addAttribute("categoryId", offerAddModel.getCategoryId());
//            }
//
//            if (!categoryService.isChosenCategoryForOfferAddCorrect(offerAddModel)) {
//                System.out.println("BAZOWA KATEGORIA: " + offerAddModel.getIsBaseCategoryChosen());
//
//                //TODO poprawić: ZEBY PO USTAWIENIU POPRAWNEJ KATEGORII BŁĄD ZNIKAŁ!
//                bindingResult.rejectValue("categoryId", "error.badCategory", "Wybierz kategorię");
//            }
//
//            return "/views/user/offer/offeradd";
//        }
}
