package com.aukcje.dto.mapper;

import com.aukcje.dto.UserStatusDTO;
import com.aukcje.entity.UserStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserStatusDTOMapper {

    UserStatusDTOMapper instance = Mappers.getMapper(UserStatusDTOMapper.class);

    @Mappings({})
    UserStatusDTO userStatusDTO(UserStatus userStatus);
}
