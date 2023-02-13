package com.aukcje.dto.mapper;

import com.aukcje.dto.UserDTO;
import com.aukcje.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserDTOMapper {

    UserDTOMapper instance = Mappers.getMapper(UserDTOMapper.class);

    @Mappings({})
    UserDTO userDTO(User user);
}
