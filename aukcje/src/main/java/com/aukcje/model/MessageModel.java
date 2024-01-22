package com.aukcje.model;

import lombok.Data;
import org.mapstruct.Mapper;

import javax.validation.constraints.Size;

@Data
public class MessageModel {

    @Size(min = 1, message = "Wpisz treść wiadomości")
    @Size(max = 500, message = "Wiadomość jest zbyt długa")
    private String content;

}
