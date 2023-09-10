package com.aukcje.dto;

import com.aukcje.enums.UserStatusEnum;
import lombok.Data;

import java.util.Objects;

@Data
public class UserDTO {

    //TODO: zmienić na private

    public long id;
    public String username;
    public String firstName;
    public String lastName;
    public String email;
    public Integer averageRate;
    public String avatarPath;
    public UserStatusDTO userStatus;

    public boolean isActive(){
        return Objects.equals(userStatus.getName(), UserStatusEnum.AKTYWNY.toString());
    }

}
