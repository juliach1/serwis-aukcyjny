package com.aukcje.controller.dictionaries;

import com.aukcje.dto.MessageDTO;
import com.aukcje.dto.NewestMessageDTO;
import com.aukcje.dto.UserDTO;
import com.aukcje.entity.User;
import com.aukcje.exception.customException.UserNotFoundException;
import com.aukcje.model.AddressModel;
import com.aukcje.model.MessageModel;
import com.aukcje.service.iface.MessageService;
import com.aukcje.service.iface.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.*;

@Controller
@RequestMapping("/wiadomosci")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;


    @GetMapping("/podglad")
    public String getSendersAndReceiversForPrincipal(Model model,
                                            Principal principal) throws UserNotFoundException {
        UserDTO principalDTO = userService.findByUsername(principal.getName());
        Long userId = principalDTO.getId();

        List<NewestMessageDTO> newestMessageDTOS = messageService.getNewestMessageChats(userId);

        model.addAttribute("newestMessageDTOS", newestMessageDTOS);

        return "/views/user/message/message-chats";
    }

    @GetMapping("/czat")
    public String getChat(@RequestParam(value = "uzytkownikId") Long userId,
                          Model model,
                          Principal principal) throws UserNotFoundException {
        UserDTO principalDTO = userService.findByUsername(principal.getName());
        UserDTO userDTO = userService.findById(userId);

        List<MessageDTO> messageDTOS = messageService.getMessagesForUserIdAndOtherUserId(principalDTO.getId(), userId);

        model.addAttribute("messageDTOS", messageDTOS);
        model.addAttribute("principalDTO", principalDTO);
        model.addAttribute("user", userDTO);
        model.addAttribute("messageModel", new MessageModel());


        return "/views/user/message/message";
    }
}
