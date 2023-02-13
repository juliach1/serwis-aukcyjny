package com.aukcje.model;

import com.aukcje.validator.FieldMatch;
import com.aukcje.validator.ValidEmail;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Constraint;
import javax.validation.Valid;
import javax.validation.constraints.*;
import javax.validation.executable.ValidateOnExecution;

@Data
@FieldMatch.List({
        @FieldMatch(first = "password", second = "matchingPassword",
                    message = "Podane hasła muszą być takie same!")
})
public class UserRegisterModel {


    @NotEmpty(message = "Podaj nazwę użytkownika")
    @Size(min=2, message = "Nazwa użytkownika musi mieć przynajmniej 2 znaki!")
    @Size(max=50, message = "Nazwa użytkownika nie może być dłuższa niż 50 znaków!")
    private String username;

    @NotEmpty(message = "Podaj hasło")
    @Size(min=5, message = "Hasło powinno mieć przynajmniej 5 znaków")
    @Size(max=80, message = "Bezpieczeństwo jest ważne, ale hasło musi mieć mniej niż 80 znaków...")
    private String password;

    @NotEmpty(message = "Powtórz swoje hasło")
    private String matchingPassword;

    @NotEmpty(message = "Podaj e-mail")
    @ValidEmail
    private String email;

    private String firstName;

    private String lastName;
}
