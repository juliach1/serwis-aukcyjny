package com.aukcje.controller.dictionaries;

import com.aukcje.dto.OfferDTO;
import com.aukcje.dto.UserAuctionDTO;
import com.aukcje.enums.OfferTypeEnum;
import com.aukcje.model.AddressModel;
import com.aukcje.service.iface.OfferService;
import com.aukcje.service.iface.UserAuctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/oferta")
public class OfferController {

    @Autowired
    OfferService offerService;

    @Autowired
    UserAuctionService userAuctionService;


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


}
