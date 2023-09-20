package com.aukcje.controller.dictionaries;

import com.aukcje.dto.UserDTO;
import com.aukcje.exception.OfferNotActiveException;
import com.aukcje.exception.PurchaseStatusNotFoundException;
import com.aukcje.exception.customException.*;
import com.aukcje.model.OfferPurchaseModel;
import com.aukcje.service.iface.OfferPurchaseService;
import com.aukcje.service.iface.UserAuctionService;
import com.aukcje.service.iface.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/oferta")
public class AuctionRestController {

    @Autowired
    private UserAuctionService userAuctionService;

    @Autowired
    private UserService userService;

    @PostMapping("/licytuj")
    public ResponseEntity<String> placeBid( Principal principal,
                                            @RequestParam("ofertaId") Long offerId,
                                            @RequestParam("kwota") Double bidAmount) throws UserNotFoundException, CanNotBidYourOfferException, OfferNotActiveException, UserNotActiveException, OfferNotFoundException {

        UserDTO userDTO = userService.findByUsername(principal.getName());
        userAuctionService.placeBid(offerId, bidAmount, userDTO.getId());

        return ResponseEntity.status(HttpStatus.OK).body("Złożono ofertę");
    }
}
