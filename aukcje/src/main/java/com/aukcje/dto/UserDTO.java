package com.aukcje.dto;

import lombok.Data;

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

}
