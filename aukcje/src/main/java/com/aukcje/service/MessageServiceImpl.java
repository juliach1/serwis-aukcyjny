package com.aukcje.service;

import com.aukcje.dto.MessageDTO;
import com.aukcje.dto.NewestMessageDTO;
import com.aukcje.dto.UserDTO;
import com.aukcje.dto.mapper.MessageDTOMapper;
import com.aukcje.entity.Message;
import com.aukcje.entity.MessageReceiver;
import com.aukcje.entity.MessageSender;
import com.aukcje.entity.User;
import com.aukcje.exception.customException.UserNotFoundException;
import com.aukcje.model.mapper.MessageModelMapper;
import com.aukcje.repository.MessageRepository;
import com.aukcje.repository.UserRepository;
import com.aukcje.service.iface.MessageService;
import com.aukcje.service.iface.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.*;

@RequiredArgsConstructor(onConstructor = @__(@Inject))
@Service
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final UserService userService;
    private final UserRepository userRepository;

    @Override
    public List<NewestMessageDTO> getNewestMessageChats(Long userId) throws UserNotFoundException {
        Set<MessageSender> senders = getSendersForReceiverUserId(userId);
        Set<MessageReceiver> receivers = getReceiversForSenderUserId(userId);

        Map<Long, LocalDateTime> newestMessageChats = new HashMap<>();
        populateNewestMessageChatsMap(userId, receivers, senders, newestMessageChats);

        return getNewestMessageListFromMap(newestMessageChats);
    }

    @Override
    public List<MessageDTO> getMessagesForUserIdAndOtherUserId(Long userId, Long otherUserId) {
       List<Message> messages = messageRepository.findByReceiverIdAndSenderIdOrSenderIdAndReceiverIdOrderBySendTime(userId, otherUserId, userId, otherUserId);
       return createMessageDTO(messages);
    }

    @Override
    public void saveMessage(Long senderId, Long receiverId, String msg) throws UserNotFoundException {
        User sender = userRepository.findById(senderId).orElseThrow(UserNotFoundException::new);
        User receiver = userRepository.findById(receiverId).orElseThrow(UserNotFoundException::new);

        Message message = MessageModelMapper.message(sender, receiver, msg);
        System.out.println("Message: "+message.getContent()+" sender: "+message.getSender().getId()+" receiver: "+message.getReceiver().getId());
        messageRepository.save(message);
    }

    private List<MessageDTO> createMessageDTO(List<Message> messages){
        List<MessageDTO> messageDTOS = new ArrayList<>();
        for(Message msg : messages){
            MessageDTO messageDTO = createMessageDTO(msg);
            messageDTOS.add(messageDTO);
        }
        return messageDTOS;
    }
    private MessageDTO createMessageDTO(Message message){
        return MessageDTOMapper.instance.messageDTO(message);
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

    private int compareDates(NewestMessageDTO newestMessage1, NewestMessageDTO newestMessage2) {
        if (newestMessage1.getSendTime().isBefore(newestMessage2.getSendTime())) {
            return 1;
        } else if (newestMessage1.getSendTime().isAfter(newestMessage2.getSendTime())) {
            return -1;
        }
        return 0;
    }
    private void populateNewestMessageChatsMap(Long userId, Set<MessageReceiver> messageReceivers, Set<MessageSender> messageSenders, Map<Long, LocalDateTime> newestMessageChars) {
        addNewestMessageChatsForReceivers(userId, messageReceivers, newestMessageChars);
        addNewestMessageChatsForSenders(userId, messageSenders, newestMessageChars);
    }
    private void addNewestMessageChatsForSenders(Long userId, Set<MessageSender> senders, Map<Long, LocalDateTime> newestMessageChats) {
        for (MessageSender sender : senders){
            LocalDateTime dateForSender = messageRepository.findTopByReceiverIdAndSenderIdOrderBySendTimeDesc(userId, sender.getSender().getId()).getSendTime();
            if(newestMessageChats.containsKey(sender.getSender().getId())){
                if(newestMessageChats.get(sender.getSender().getId()).isBefore(dateForSender)){
                    newestMessageChats.replace(sender.getSender().getId(), dateForSender);
                }
            }else {
                newestMessageChats.put(sender.getSender().getId(), dateForSender);
            }
        }
    }

    private void addNewestMessageChatsForReceivers(Long userId, Set<MessageReceiver> receivers, Map<Long, LocalDateTime> newestMessageChats) {
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

    private Set<MessageSender> getSendersForReceiverUserId(Long receiverId) {
        return messageRepository.findByReceiverIdOrderBySendTimeDesc(receiverId);
    }

    private Set<MessageReceiver> getReceiversForSenderUserId(Long senderId) {
        return messageRepository.findBySenderIdOrderBySendTimeDesc(senderId);
    }

}
