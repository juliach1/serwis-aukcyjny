package com.aukcje.service.iface;

import com.aukcje.dto.NewestMessageDTO;
import com.aukcje.exception.customException.UserNotFoundException;

import java.util.List;

public interface MessageService {

    List<NewestMessageDTO> getNewestMessageChats(Long userId) throws UserNotFoundException;
}
