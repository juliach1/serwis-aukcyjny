package com.aukcje.controller.dictionaries;

import com.aukcje.dto.UserDTO;
import com.aukcje.entity.User;
import com.aukcje.model.UserRegisterModel;
import com.aukcje.service.iface.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("")
public class LoginController {

    @Autowired
    UserService userService;

    //zamienia puste stringi na nulle
    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }


    @GetMapping("/logowanie")
    public String showLoginForm(){
        return "login";
    }

    @GetMapping("/rejestracja")
    public String showRegisterForm(Model model) {
        model.addAttribute("userRegisterModel", new UserRegisterModel());
        return "register";
    }


    @PostMapping("/rejestracja/autoryzacja")
    public String processRegistrationForm(
            @Valid @ModelAttribute("userRegisterModel") UserRegisterModel userRegisterModel,
            BindingResult theBindingResult,
            Model model) {

        if(theBindingResult.hasErrors()){
            return "register";
        }

        UserDTO existingUser = userService.findByUsername(userRegisterModel.getUsername());
        if(existingUser != null){
            model.addAttribute("userRegisterModel", new UserRegisterModel());
            model.addAttribute("registrationError", "Użytkownik o podanej nazwie już istnieje! Wybierz inną nazwę.");
            return "register";
        }

        userService.save(userRegisterModel);

        return "register-authorized";

    }

}
