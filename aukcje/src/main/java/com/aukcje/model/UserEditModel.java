package com.aukcje.model;

import lombok.Data;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class UserEditModel {

    private Long id;

    @NotEmpty(message = "Nazwa użytkownika nie może być pusta!")
    @Size(min=2, message = "Nazwa użytkownika musi mieć przynajmniej 2 znaki!")
    @Size(max=50, message = "Nazwa użytkownika musi być krótsza niż 50 znaków.")
    private String username;

    private String firstName;
    private String lastName;

    @NotEmpty(message = "Podaj adres e-mail")
    private String email;

    private Integer userStatus;
}
