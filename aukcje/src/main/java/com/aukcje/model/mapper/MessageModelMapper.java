package com.aukcje.model.mapper;

import com.aukcje.entity.*;
import com.aukcje.model.OfferAddModel;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import java.time.LocalDateTime;

@Mapper
public interface MessageModelMapper {

    MessageModelMapper instance = Mappers.getMapper(MessageModelMapper.class);

    @Named("message")
    static Message message(User sender, User receiver, String msg) {
        Message message = new Message();
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setContent(msg);
        message.setSendTime(LocalDateTime.now());

        return message;
    }

}


