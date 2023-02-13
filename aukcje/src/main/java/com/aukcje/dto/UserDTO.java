package com.aukcje.dto;

import com.aukcje.entity.UserStatus;
import lombok.Data;

@Data
public class UserDTO {

    public long id;
    public String username;
    public String firstName;
    public String lastName;
    public String email;
    public Integer averageRate;
    public String avatarPath;
    public UserStatusDTO userStatus;

}
