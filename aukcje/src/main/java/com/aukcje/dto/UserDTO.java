package com.aukcje.dto;

import com.aukcje.enums.UserStatusEnum;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Objects;

@Data
public class UserDTO {

    private long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private Integer averageRate;
    private String avatarPath;
    private UserStatusDTO userStatus;
    private LocalDateTime registrationDate;

    public boolean isActive(){
        return Objects.equals(userStatus.getName(), UserStatusEnum.ACTIVE.toString());
    }

}
