package com.aukcje.controller.dictionaries;

import com.aukcje.dto.UserStatusDTO;
import com.aukcje.exception.IncorrectUserStatusException;
import com.aukcje.model.UserEditModel;
import com.aukcje.model.UserSearchModel;
import com.aukcje.service.iface.UserService;
import com.aukcje.service.iface.UserStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/admin/uzytkownik")
//@RequiredArgsConstructor(onConstructor_ = @__({@Inject}))
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserStatusService userStatusService;

    @GetMapping("")
    public String searchUser(Model model,
                             @Valid @ModelAttribute("userSearchModel") UserSearchModel userSearchModel,
                             @RequestParam(value = "fraza", required = false) String phrase,
                             @RequestParam(value = "rozmiarStrony", required = false) Integer pageSize,
                             @RequestParam(value = "isActive", required = false) String isActive,
                             @RequestParam(value = "isBlocked", required = false) String isBlocked,
                             @RequestParam(value = "isDeleted", required = false) String isDeleted){

        model.addAttribute("users", userService.searchBySearchModel(userSearchModel));
        return "/views/admin/user/usersearch";
    }

    @GetMapping("/zablokuj/{uzytkownikId}")
    public String blockUser(@PathVariable(value = "uzytkownikId") Long userId){
        userService.setUserBlocked(userId);
        return "redirect:/admin/uzytkownik";
    }

    @GetMapping("/odblokuj/{uzytkownikId}")
    public String unblockUser(@PathVariable(value = "uzytkownikId") Long userId){
        userService.setUserActive(userId);
        return "redirect:/admin/uzytkownik";
    }

    @GetMapping("/edytuj/{uzytkownikId}")
    public String editUser(@PathVariable(value = "uzytkownikId") Long userId,
                                            Model model){
        model.addAttribute("userDTO", userService.findById(userId));
        model.addAttribute("userEditModel", new UserEditModel());
        model.addAttribute("statuses", userStatusService.findAll());

        return "/views/admin/user/useredit";
    }

    @PostMapping("/edytuj/przetworz")
    public String editUserProcess(Model model,
                                  @Valid @ModelAttribute("userEditModel") UserEditModel userEditModel,
                                  BindingResult bindingResult) throws IncorrectUserStatusException {
        if(bindingResult.hasErrors()){
            model.addAttribute("userEditModel", userEditModel);
            model.addAttribute("statuses", userStatusService.findAll());
            return "/views/admin/user/useredit";
        }
        if(isUserStatusCorrect(userEditModel.getUserStatus())){
            userService.updateEditUser(userEditModel);
        }else {
            throw new IncorrectUserStatusException();
        }


        return "redirect:/admin/uzytkownik";
    }

    @GetMapping("/usun/{uzytkownikId}")
    public String removeUser(@PathVariable("uzytkownikId") Long userId){
        userService.setUserDeleted(userId);
        return "redirect:/admin/uzytkownik";
    }

    private boolean isUserStatusCorrect(Integer userStatus){
        List<UserStatusDTO> userStatusDTOS = userStatusService.findAll();
        for(UserStatusDTO userStatusDTO : userStatusDTOS){
            if(Objects.equals( userStatusDTO.getId(), userStatus )){
                return true;
            }
        }
        return false;
    }
}
