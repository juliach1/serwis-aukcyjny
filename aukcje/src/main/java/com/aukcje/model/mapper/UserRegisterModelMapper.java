package com.aukcje.model.mapper;

import com.aukcje.entity.User;
import com.aukcje.model.UserRegisterModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserRegisterModelMapper {

    UserRegisterModelMapper instance = Mappers.getMapper(UserRegisterModelMapper.class);

    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "userStatus", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    User user(UserRegisterModel userRegisterModelModel);
}
