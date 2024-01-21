package com.aukcje.dto.mapper;

import com.aukcje.dto.MessageDTO;
import com.aukcje.entity.Message;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;


@Mapper
public interface MessageDTOMapper {

    MessageDTOMapper instance = Mappers.getMapper(MessageDTOMapper.class);

    @Mappings({})
    MessageDTO messageDTO(Message message);
}
