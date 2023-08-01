package com.aukcje.controller.dictionaries;

import com.aukcje.dto.CountryDTO;
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


//TODO dodać: podgląd adresów
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

        return "/views/user/address/addressadd";
    }

    @PostMapping("/dodaj/przetworz")
    public String addAddressProcess(Principal principal,
                                    Model model,
                                    @Valid @ModelAttribute("addressModel") AddressModel addressModel,
                                    BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            model.addAttribute("addressAddModel", new AddressModel());
            model.addAttribute("countries", countryService.findAll());

            return "/views/user/address/addressadd";
        }

        CountryDTO chosenCountry = countryService.findByName(addressModel.getCountry());

        if(chosenCountry != null){
            addressService.save(addressModel, userService.findByUsername(principal.getName()).getId() );
        }else{
            //TODO [wyjątek]: incorrect country
        }

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

            return "/views/user/address/addressedit";
        }else {
            //TODO [pytanie] : Lepiej rzuć wyjąte, czy zostawić tak - przenieść na dodawanie?
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

            return "/views/user/address/addressedit";
        }


        CountryDTO chosenCountry = countryService.findByName(addressModel.getCountry());

        if(chosenCountry != null){
            Long userId = userService.findByUsername(principal.getName()).getId();
            addressService.updateAddress(addressModel, userId);
        }else{
            //TODO [wyjątek]: incorrect country ex
        }

        return "redirect:/uzytkownik/adres/dodaj";
    }
}
