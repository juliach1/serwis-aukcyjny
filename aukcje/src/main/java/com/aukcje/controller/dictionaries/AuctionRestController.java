package com.aukcje.controller.dictionaries;

import com.aukcje.dto.UserDTO;
import com.aukcje.enums.BidStatusEnum;
import com.aukcje.exception.OfferNotActiveException;
import com.aukcje.exception.customException.CanNotBidYourOfferException;
import com.aukcje.exception.customException.OfferNotFoundException;
import com.aukcje.exception.customException.UserNotActiveException;
import com.aukcje.exception.customException.UserNotFoundException;
import com.aukcje.service.iface.UserAuctionService;
import com.aukcje.service.iface.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.inject.Inject;
import java.security.Principal;

@RestController
@RequestMapping("/oferta")
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class AuctionRestController {

    private final UserAuctionService userAuctionService;
    private final UserService userService;

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

        return ResponseEntity.status(httpStatus).body(status.toString());
    }
}
