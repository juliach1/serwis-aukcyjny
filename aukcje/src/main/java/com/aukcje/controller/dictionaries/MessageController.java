package com.aukcje.controller.dictionaries;

import com.aukcje.dto.MessageDTO;
import com.aukcje.dto.NewestMessageDTO;
import com.aukcje.dto.UserDTO;
import com.aukcje.exception.customException.UserNotFoundException;
import com.aukcje.model.MessageModel;
import com.aukcje.service.iface.MessageService;
import com.aukcje.service.iface.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.inject.Inject;
import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor(onConstructor = @__(@Inject))
@RequestMapping("/wiadomosci")
public class MessageController {

    private final MessageService messageService;
    private final UserService userService;

    @GetMapping("/podglad")
    public String getSendersAndReceiversForPrincipal(Model model, Principal principal)
            throws UserNotFoundException {
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
