package com.aukcje.model.mapper;

import com.aukcje.entity.User;
import com.aukcje.model.UserEditModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserEditModelMapper {

    UserEditModelMapper instance = Mappers.getMapper(UserEditModelMapper.class);

    @Mapping(target = "userStatus", ignore = true)
    User user(UserEditModel userModel);

}
