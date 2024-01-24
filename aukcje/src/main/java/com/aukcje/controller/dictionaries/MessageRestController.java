package com.aukcje.controller.dictionaries;

import com.aukcje.dto.UserDTO;
import com.aukcje.exception.customException.UserNotFoundException;
import com.aukcje.model.MessageModel;
import com.aukcje.service.iface.MessageService;
import com.aukcje.service.iface.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Inject))
@RequestMapping("/wiadomosci")
public class MessageRestController {

    private final MessageService messageService;
    private final UserService userService;

    @PostMapping("/wyslij")
    public void deleteCartOffer(Principal principal,
                                @Valid @RequestBody MessageModel messageModel) throws UserNotFoundException {
        UserDTO principalDTO = userService.findByUsername(principal.getName());

        Long senderId = principalDTO.getId();
        Long receiverId = messageModel.getReceiverId();
        String msg = messageModel.getContent();

        messageService.saveMessage(senderId, receiverId, msg);
    }
}
