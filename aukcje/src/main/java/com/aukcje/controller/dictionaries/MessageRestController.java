package com.aukcje.controller.dictionaries;

import com.aukcje.dto.UserDTO;
import com.aukcje.exception.customException.UserNotFoundException;
import com.aukcje.model.MessageModel;
import com.aukcje.service.iface.MessageService;
import com.aukcje.service.iface.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/wiadomosci")
public class MessageRestController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @PostMapping("/wyslij")
    public void deleteCartOffer(Principal principal,
                                @Valid @RequestBody MessageModel messageModel) throws UserNotFoundException {
        UserDTO principalDTO = userService.findByUsername(principal.getName());

        Long senderId = principalDTO.getId();
        Long receiverId = messageModel.getReceiverId();
        String msg = messageModel.getContent();
        System.out.println("Sender: "+senderId+" Receiver: "+receiverId+" Msg: "+msg);

        messageService.saveMessage(senderId, receiverId, msg);
    }
}
