package com.aukcje.controller.dictionaries;

import com.aukcje.model.MessageModel;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/wiadomosci")
public class MessageRestController {

    @PostMapping("/wyslij")
    public void deleteCartOffer( @Valid @RequestBody MessageModel messageModel){
        System.out.println(messageModel.getContent());
    }
}
