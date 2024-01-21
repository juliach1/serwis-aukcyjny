package com.aukcje.service;

import com.aukcje.dto.NewestMessageDTO;
import com.aukcje.dto.UserDTO;
import com.aukcje.entity.MessageReceiver;
import com.aukcje.entity.MessageSender;
import com.aukcje.exception.customException.UserNotFoundException;
import com.aukcje.repository.MessageRepository;
import com.aukcje.service.iface.MessageService;
import com.aukcje.service.iface.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.image.Kernel;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private UserService userService;
    @Override
    public List<NewestMessageDTO> getNewestMessageChats(Long userId) throws UserNotFoundException {

        Set<MessageSender> senders = getSendersForReceiverUserId(userId);
        Set<MessageReceiver> receivers = getReceiversForSenderUserId(userId);

        Map<Long, LocalDateTime> newestMessageChats = new HashMap<>();
        populateNewestMessageChatsMap(userId, receivers, senders, newestMessageChats);

        return getNewestMessageListFromMap(newestMessageChats);
    }

    private List<NewestMessageDTO> getNewestMessageListFromMap(Map<Long, LocalDateTime> newestMessageMap) throws UserNotFoundException {
        List<NewestMessageDTO> newestMessageDTOS = new ArrayList<>();
        for (Long key : newestMessageMap.keySet()) {
            UserDTO otherUserDTO = userService.findById(key);
            NewestMessageDTO newestMessageDTO = new NewestMessageDTO(otherUserDTO, newestMessageMap.get(key));
            newestMessageDTOS.add(newestMessageDTO);
        }

        newestMessageDTOS.sort(this::compareDates);

        return newestMessageDTOS;
    }

    private int compareDates(NewestMessageDTO newestMessage1, NewestMessageDTO newestMessage2){
        if (newestMessage1.getSendTime().isBefore(newestMessage2.getSendTime())) {
            return 1;
        } else if (newestMessage1.getSendTime().isAfter(newestMessage2.getSendTime())) {
            return -1;
        }
        return 0;
    }
    private void populateNewestMessageChatsMap(Long userId, Set<MessageReceiver> messageReceivers, Set<MessageSender> messageSenders, Map<Long, LocalDateTime> newestMessageChars){
        addNewestMessageChatsForReceivers(userId, messageReceivers, newestMessageChars);
        addNewestMessageChatsForSenders(userId, messageSenders, newestMessageChars);
    }
    private void addNewestMessageChatsForSenders(Long userId, Set<MessageSender> senders, Map<Long, LocalDateTime> newestMessageChats){
        for (MessageSender sender : senders){
            LocalDateTime dateForSender = messageRepository.findTopMessageByReceiverIdAndSenderIdOrderBySendTimeDesc(userId, sender.getSender().getId()).getSendTime();
            if(newestMessageChats.containsKey(sender.getSender().getId())){
                if(newestMessageChats.get(sender.getSender().getId()).isBefore(dateForSender)){
                    newestMessageChats.replace(sender.getSender().getId(), dateForSender);
                }
            }else {
                newestMessageChats.put(sender.getSender().getId(), dateForSender);
            }
        }
    }

    private void addNewestMessageChatsForReceivers(Long userId, Set<MessageReceiver> receivers, Map<Long, LocalDateTime> newestMessageChats){
        for (MessageReceiver receiver : receivers){
            LocalDateTime dateForReceiver = messageRepository.findTopByReceiverIdAndSenderIdOrderBySendTimeDesc( receiver.getReceiver().getId(), userId).getSendTime();

            if(newestMessageChats.containsKey(receiver.getReceiver().getId())){
                if(newestMessageChats.get(receiver.getReceiver().getId()).isBefore(dateForReceiver)){
                    newestMessageChats.replace(receiver.getReceiver().getId(), dateForReceiver);
                }
            }else {     //JEŚLI NIE POSIADA ELEMENTU
                newestMessageChats.put(receiver.getReceiver().getId(), dateForReceiver);
            }
        }
    }

    private Set<MessageSender> getSendersForReceiverUserId(Long receiverId){
        return messageRepository.findByReceiverIdOrderBySendTimeDesc(receiverId);
    }

    private Set<MessageReceiver> getReceiversForSenderUserId(Long senderId){
        return messageRepository.findBySenderIdOrderBySendTimeDesc(senderId);
    }

}
