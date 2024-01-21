package com.aukcje.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MessageDTO {

    private UserDTO receiver;
    private UserDTO user;
    private String messageContent;
    private LocalDateTime sendTime;
}
