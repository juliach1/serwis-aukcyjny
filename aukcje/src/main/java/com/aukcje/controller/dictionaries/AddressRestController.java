package com.aukcje.controller.dictionaries;

import com.aukcje.dto.AddressDTO;
import com.aukcje.dto.OfferPurchaseInfoDTO;
import com.aukcje.dto.UserDTO;
import com.aukcje.enums.PurchaseStatusEnum;
import com.aukcje.exception.customException.AddressNotFoundException;
import com.aukcje.exception.customException.PermissionDeniedException;
import com.aukcje.service.iface.AddressService;
import com.aukcje.service.iface.OfferPurchaseService;
import com.aukcje.service.iface.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

import static com.aukcje.service.iface.OfferPurchaseService.*;

@RestController
@RequestMapping("/uzytkownik/adres")
public class AddressRestController {


    @Autowired
    AddressService addressService;

    @Autowired
    UserService userService;

    @GetMapping("/usun/{adresId}")
    public void addOfferToCart(HttpServletResponse response,
                               Principal principal,
                               @PathVariable("adresId") Long addressId) throws AddressNotFoundException {

        System.out.println("W KONTROLERZE USUWANIA ADRESU");
        UserDTO user = userService.findByUsername(principal.getName());
        addressService.findNotDeletedById(addressId);

        if(addressService.isAddressAssignedToUser(user.getId(), addressId)){
            addressService.deleteAddress(addressId);
            response.setStatus(HttpServletResponse.SC_ACCEPTED);
        }else
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);

    }

}
