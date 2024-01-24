package com.aukcje.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class NewestMessageDTO {

    private UserDTO otherUser;
    private LocalDateTime sendTime;

}

