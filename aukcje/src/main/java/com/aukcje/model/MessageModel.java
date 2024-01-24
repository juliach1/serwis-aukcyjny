package com.aukcje.model;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class MessageModel {

    @Size(min = 1, message = "Wpisz treść wiadomości")
    @Size(max = 500, message = "Wiadomość jest zbyt długa")
    private String content;

    @NotNull(message = "Nie znaleziono konta odbiorcy")
    private Long receiverId;
}
