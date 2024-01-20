package com.aukcje.controller.dictionaries;

import com.aukcje.dto.NewestMessage;
import com.aukcje.entity.MessageReceiver;
import com.aukcje.entity.MessageSender;
import com.aukcje.repository.MessageRepository;
import com.aukcje.service.iface.UserService;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.*;

@Controller
@RequestMapping("/wiadomosci")
public class MessagesController {

    @Autowired
    MessageRepository messageRepository;
    UserService userService;

    @GetMapping("/podglad")
    void getSendersAndReceiversForPrincipal(Model model,
                                            Principal principal){

        //POBIERAM UŻYTKOWNIKÓW Z KTÓRYMI MIAŁ INTERAKCJĘ
        Set<MessageSender> senders = messageRepository.findByReceiverIdOrderBySendTimeDesc(2l);
        Set<MessageReceiver> receivers = messageRepository.findBySenderIdOrderBySendTimeDesc(2l);

        //ROBIĘ SETA NA NAJNOWSZE WIADOMOŚCI
        Map<Long, LocalDateTime> newestMessages = new HashMap<>();

        //WYPEŁNIAM GO RECEIVERAMI
        for (MessageReceiver receiver : receivers){
            LocalDateTime dateForReceiver = messageRepository.findTopByReceiverIdAndSenderIdOrderBySendTimeDesc( receiver.getReceiver().getId(), 2l).getSendTime();

            if(newestMessages.containsKey(receiver.getReceiver().getId())){ //JEŚLI POSIADA JUŻ TAKI ELEMENT
                if(newestMessages.get(receiver.getReceiver().getId()).isBefore(dateForReceiver)){
                    newestMessages.replace(receiver.getReceiver().getId(), dateForReceiver);
                }
            }else {     //JEŚLI NIE POSIADA ELEMENTU
                newestMessages.put(receiver.getReceiver().getId(), dateForReceiver);
            }
        }
        for (MessageSender sender : senders){
            LocalDateTime dateForSender = messageRepository.findTopByReceiverIdAndSenderIdOrderBySendTimeDesc(2l, sender.getSender().getId()).getSendTime();
            if(newestMessages.containsKey(sender.getSender().getId())){
                if(newestMessages.get(sender.getSender().getId()).isBefore(dateForSender)){
                    newestMessages.replace(sender.getSender().getId(), dateForSender);
                }
            }else {
                newestMessages.put(sender.getSender().getId(), dateForSender);
            }

        }

        for(Long key : newestMessages.keySet()){
            System.out.println("User: "+key+" Data: "+newestMessages.get(key));
        }
    }
}
