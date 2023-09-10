package com.aukcje.controller.dictionaries;

import com.aukcje.model.OfferPurchaseModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/zakup")
public class PurchaseController {

    @PostMapping("/przetworz")
    public String newPurchase(Principal principal, @RequestBody List<OfferPurchaseModel> offerPurchaseModel){

        System.out.println("ZAKUPIONO PRZEDMIOTY: ");
        for (OfferPurchaseModel offerPurchaseModel1 : offerPurchaseModel){
            System.out.println("ID: "+offerPurchaseModel1.getOfferId());
            System.out.println("liczba: "+offerPurchaseModel1.getQuantity());
        }



        return "redirect:/uzytkownik/strona-glowna";
    }

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
