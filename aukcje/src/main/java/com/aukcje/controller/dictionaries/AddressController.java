package com.aukcje.controller.dictionaries;

import com.aukcje.dto.AddressDTO;
import com.aukcje.model.AddressModel;
import com.aukcje.service.iface.AddressService;
import com.aukcje.service.iface.CountryService;
import com.aukcje.service.iface.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

//TODO: Dodać podgląd adresów
@Controller
@RequestMapping("/uzytkownik/adres")
public class AddressController {

    @Autowired
    AddressService addressService;

    @Autowired
    UserService userService;

    @Autowired
    CountryService countryService;

    @GetMapping("/dodaj")
    public String addAddress(Model model){
        model.addAttribute("countries", countryService.findAll());
        model.addAttribute("addressModel", new AddressModel());

        return "/user/address/addressadd";
    }

    @PostMapping("/dodaj/przetworz")
    public String addAddressProcess(Principal principal,
                                    Model model,
                                    @Valid @ModelAttribute("addressModel") AddressModel addressModel,
                                    BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            model.addAttribute("addressAddModel", new AddressModel());
            model.addAttribute("countries", countryService.findAll());

            return "/user/address/addressadd";
        }

        addressService.save(addressModel, userService.findByUsername(principal.getName()).getId() );
        return "redirect:/uzytkownik/adres/dodaj";
    }

    @GetMapping("/edytuj/{adresId}")
    public String editAddress(Principal principal,
                              @PathVariable("adresId") Long addressId,
                              Model model){

        Long userId = userService.findByUsername(principal.getName()).getId();

        if( addressService.isAddressAssignedToUser(userId, addressId) ){

            model.addAttribute("countries", countryService.findAll());
            model.addAttribute("addressDTO", addressService.findById(addressId));
            model.addAttribute("addressModel", new AddressModel());

            return "/user/address/addressedit";
        }

        return "redirect:/uzytkownik/adres/dodaj";
    }

    @PostMapping("/edytuj/przetworz")
    public String editAddressProcess( Principal principal,
                                      Model model,
                                      @Valid @ModelAttribute("addressModel") AddressModel addressModel,
                                      BindingResult bindingResult ){
        if(bindingResult.hasErrors()){
            model.addAttribute("countries", countryService.findAll());
            model.addAttribute("addressModel", addressModel);

            return "/user/address/addressedit";
        }

        Long userId = userService.findByUsername(principal.getName()).getId();
        addressService.updateAddress(addressModel, userId);
        return "redirect:/uzytkownik/adres/dodaj";
    }
}
