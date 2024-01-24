package com.aukcje.controller.dictionaries;

import com.aukcje.dto.OfferPurchaseInfoDTO;
import com.aukcje.dto.UserDTO;
import com.aukcje.enums.PurchaseStatusEnum;
import com.aukcje.exception.customException.PermissionDeniedException;
import com.aukcje.service.iface.OfferPurchaseService;
import com.aukcje.service.iface.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

import static com.aukcje.service.iface.OfferPurchaseService.*;

@RequiredArgsConstructor(onConstructor = @__(@Inject))
@PreAuthorize("hasRole('ROLE_USER')")
@RestController
@RequestMapping("/zakup")
public class PurchaseRestController {
    private final UserService userService;
    private final OfferPurchaseService offerPurchaseService;

    @GetMapping("/zmien-status/{purchaseId}")
    public void addOfferToCart(HttpServletResponse response,
                               Principal principal,
                               @PathVariable("purchaseId") Long purchaseId,
                               @RequestParam("statusId") Integer statusId) throws PermissionDeniedException {

        UserDTO user = userService.findByUsername(principal.getName());
        OfferPurchaseInfoDTO purchaseInfoDTO = offerPurchaseService.getById(purchaseId);

        if(isUserBuyer(user, purchaseInfoDTO) && isStatus(PurchaseStatusEnum.SENT, purchaseInfoDTO)) {
            offerPurchaseService.updatePurchaseStatus(purchaseId, statusId);
            response.setStatus(HttpServletResponse.SC_ACCEPTED);

        } else if (isUserSeller(user, purchaseInfoDTO) && isStatus(PurchaseStatusEnum.NEW, purchaseInfoDTO)) {
            offerPurchaseService.updatePurchaseStatus(purchaseId, statusId);
            response.setStatus(HttpServletResponse.SC_ACCEPTED);

        } else {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            throw new PermissionDeniedException();
        }
    }
}
