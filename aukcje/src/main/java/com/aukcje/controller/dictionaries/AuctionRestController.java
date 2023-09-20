package com.aukcje.controller.dictionaries;

import com.aukcje.dto.UserDTO;
import com.aukcje.enums.BidStatusEnum;
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

    @GetMapping("/licytuj")
    public ResponseEntity<String> placeBid( Principal principal,
                                            @RequestParam("ofertaId") Long offerId,
                                            @RequestParam("kwota") Double bidAmount) throws UserNotFoundException, CanNotBidYourOfferException, OfferNotActiveException, UserNotActiveException, OfferNotFoundException {

        UserDTO userDTO = userService.findByUsername(principal.getName());
        BidStatusEnum status = userAuctionService.placeBid(offerId, bidAmount, userDTO.getId());


        HttpStatus httpStatus;
        switch (status){
            case BID_PLACED:
                httpStatus = HttpStatus.CREATED; break;
            case TOO_LOW:
                httpStatus = HttpStatus.CONFLICT; break;
            case AUCTION_ENDED:
                 httpStatus = HttpStatus.OK; break;
            default:
                httpStatus = HttpStatus.BAD_REQUEST; break;
        }

        ResponseEntity<String> responseEntity = ResponseEntity.status(httpStatus).body(status.toString());

        System.out.println("STATUS:" + responseEntity.getStatusCode().name());
        return responseEntity;
    }
}
