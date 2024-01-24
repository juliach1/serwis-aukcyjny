package com.aukcje.controller.dictionaries;

import com.aukcje.exception.customException.OfferNotFoundException;
import com.aukcje.exception.customException.OfferStatusNotFoundException;
import com.aukcje.service.iface.OfferService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.inject.Inject;

@RequiredArgsConstructor(onConstructor = @__(@Inject))
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
@Controller
@RequestMapping("/admin/oferta")
public class OfferAdminController {

    private final OfferService offerService;

    @RequestMapping("/wstrzymaj/{offerId}")
    private String zablokujOfertę(@PathVariable("offerId") Long offerId) throws OfferNotFoundException, OfferStatusNotFoundException {
        offerService.setOfferSuspended(offerId);
        //POBIERZ OFERTĘ
        //JEŚLI AKTYWNA, zmień status na WSTRZYMANA
        return "";
    }
}
