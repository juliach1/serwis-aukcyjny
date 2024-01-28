package com.aukcje.controller.dictionaries;

import com.aukcje.dto.UserDTO;
import com.aukcje.exception.customException.UserStatusNotFoundException;
import com.aukcje.model.UserRegisterModel;
import com.aukcje.service.iface.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;

@Controller
@RequiredArgsConstructor(onConstructor = @__(@Inject))
@RequestMapping("")
public class LoginController {

    private final UserService userService;

    //TODO PRZENIEŚĆ ZAWARTOŚĆ METODY DO INNEJ KLASY
    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping("/logowanie")
    public String showLoginForm(){
        return "/views/login";
    }
    //TODO dodać: BLOKADA DLA ZABLOKOWANYCH USERÓW

    @GetMapping("/rejestracja")
    public String showRegisterForm(Model model) {
        model.addAttribute("userRegisterModel", new UserRegisterModel());
        return "/views/register";
    }

    @PostMapping("/rejestracja/autoryzacja")
    public String processRegistrationForm(
            @Valid @ModelAttribute("userRegisterModel") UserRegisterModel userRegisterModel,
            BindingResult theBindingResult,
            Model model) throws UserStatusNotFoundException {

        if(theBindingResult.hasErrors())
            return "/views/register";

        UserDTO existingUser = userService.findByUsername(userRegisterModel.getUsername());
        if(existingUser != null) {
            model.addAttribute("userRegisterModel", new UserRegisterModel());
            model.addAttribute("registrationError", "Użytkownik o podanej nazwie już istnieje! Wybierz inną nazwę.");
            return "/views/register";
        }

        userService.save(userRegisterModel);

        return "register-authorized";
    }

}
