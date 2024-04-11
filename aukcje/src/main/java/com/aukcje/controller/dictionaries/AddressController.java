package com.aukcje.controller.dictionaries;

import com.aukcje.dto.AddressDTO;
import com.aukcje.dto.CountryDTO;
import com.aukcje.dto.UserDTO;
import com.aukcje.exception.customException.AddressEditPermissionDeniedException;
import com.aukcje.exception.customException.AddressNotFoundException;
import com.aukcje.exception.customException.IncorrectCountryException;
import com.aukcje.model.AddressModel;
import com.aukcje.service.iface.AddressService;
import com.aukcje.service.iface.CountryService;
import com.aukcje.service.iface.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.inject.Inject;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor(onConstructor = @__(@Inject))
@RequestMapping("/uzytkownik/adres")
public class AddressController {

    private final AddressService addressService;
    private final UserService userService;
    private final CountryService countryService;

    @GetMapping("")
    public String userAddresses(Principal principal, Model model) {
        UserDTO userDTO = userService.findByUsername(principal.getName());
        Long userId = userDTO.getId();
        List<AddressDTO> addressDTOS = addressService.findNotDeletedByUserId(userId);

        model.addAttribute("addressDTOS", addressDTOS);

        return "/views/user/address/address-all";
    }

    @GetMapping("/dodaj")
    public String addAddress(Model model) {
        List<CountryDTO> countries = countryService.findAll();

        model.addAttribute("countries", countries);
        model.addAttribute("addressModel", new AddressModel());

        return "/views/user/address/address-add";
    }

    @PostMapping("/dodaj/przetworz")
    public String addAddressProcess(Principal principal,
                                    Model model,
                                    @Valid @ModelAttribute("addressModel") AddressModel addressModel,
                                    BindingResult bindingResult) throws IncorrectCountryException {

        if(bindingResult.hasErrors()) {
            model.addAttribute("addressModel", new AddressModel());
            model.addAttribute("countries", countryService.findAll());
            return "/views/user/address/address-add";
        }

        CountryDTO chosenCountry = countryService.findByName(addressModel.getCountry());

        if(chosenCountry != null)
            addressService.save(addressModel, userService.findByUsername(principal.getName()).getId() );
        else
            throw new IncorrectCountryException();

        return "redirect:/uzytkownik/adres/dodaj";
    }

    @GetMapping("/edytuj/{adresId}")
    public String editAddress(Principal principal,
                              @PathVariable("adresId") Long addressId,
                              Model model) throws AddressEditPermissionDeniedException, AddressNotFoundException {

        Long userId = userService.findByUsername(principal.getName()).getId();
        AddressDTO addressDTO = addressService.findNotDeletedById(addressId);
        AddressModel addressModel = new AddressModel();
        addressModel.setId(addressId);

        if(addressDTO == null) throw new AddressNotFoundException();

        if(addressService.isAddressAssignedToUser(userId, addressId)) {
            model.addAttribute("countries", countryService.findAll());
            model.addAttribute("addressDTO", addressDTO);
            model.addAttribute("addressModel", addressModel);

            return "/views/user/address/address-edit";
        } else
            throw new AddressEditPermissionDeniedException();

    }

    @PostMapping("/edytuj/przetworz")
    public String editAddressProcess( Principal principal,
                                      Model model,
                                      @Valid @ModelAttribute("addressModel") AddressModel addressModel,
                                      BindingResult bindingResult ) throws IncorrectCountryException, AddressNotFoundException {

        List<CountryDTO> countries = countryService.findAll();

        if(bindingResult.hasErrors()) {
            model.addAttribute("countries", countries);
            model.addAttribute("addressModel", addressModel);

            return "/views/user/address/address-edit";
        }

        CountryDTO chosenCountry = countryService.findByName(addressModel.getCountry());

        if(chosenCountry != null){
            Long userId = userService.findByUsername(principal.getName()).getId();
            addressService.updateAddress(addressModel, userId);
        }else
            throw new IncorrectCountryException();

        return "redirect:/uzytkownik/adres";
    }
}
