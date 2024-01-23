package com.aukcje.service.iface;

import com.aukcje.dto.MessageDTO;
import com.aukcje.dto.NewestMessageDTO;
import com.aukcje.exception.customException.UserNotFoundException;

import java.util.List;

public interface MessageService {

    List<NewestMessageDTO> getNewestMessageChats(Long userId) throws UserNotFoundException;

    List<MessageDTO> getMessagesForUserIdAndOtherUserId(Long userId, Long otherUserId);

    void saveMessage(Long senderId, Long receiverId, String msg) throws UserNotFoundException;
}
