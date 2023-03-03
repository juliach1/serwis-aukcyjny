package com.aukcje.controller.dictionaries;

import com.aukcje.model.AddressModel;
import com.aukcje.service.iface.OfferService;
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


    @GetMapping("/podglad/{ofertaId}")
    public String getOffer(@PathVariable("ofertaId") Long offerId, Model model){


        model.addAttribute("offerDTO", offerService.findById(offerId) );

        return "/user/offer/offer";
    }
}
