package com.aukcje.controller.dictionaries;

import com.aukcje.dto.UserDTO;
import com.aukcje.exception.customException.AddressNotFoundException;
import com.aukcje.service.iface.AddressService;
import com.aukcje.service.iface.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

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

        UserDTO user = userService.findByUsername(principal.getName());
        addressService.findNotDeletedById(addressId);

        if(addressService.isAddressAssignedToUser(user.getId(), addressId)){
            addressService.deleteAddress(addressId);
            response.setStatus(HttpServletResponse.SC_ACCEPTED);
        }else
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);

    }

}
