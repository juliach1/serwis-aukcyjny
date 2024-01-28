package com.aukcje.model;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class AddressModel {

    @NotNull
    private Long id;

    @NotEmpty(message = "Wybierz kraj")
    private String country;

    @NotEmpty(message = "Podaj imię")
    @Size(max=50, message = "Imię może mieć maksymalnie 50 znaków.")
    private String firstName;

    @NotEmpty(message = "Podaj nazwisko")
    @Size(max=50, message = "Nazwisko może mieć maksymalnie 50 znaków.")
    private String lastName;

    @NotEmpty(message = "Podaj adres")
    @Size(max=50, message = "Adres może mieć maksymalnie 100 znaków!")
    private String streetName;

    @Size(max=10, message = "Kod pocztowy może mieć maksymalnie 10 znaków")
    private String postalCode;

    @NotEmpty(message = "Numer telefonu jest wymagany")
    @Size(max=20, message = "Numer telefonu może mieć maksymalnie 20 znaków")
    private String phone;

    @NotEmpty(message = "Miasto jest wymagane")
    @Size(max=50, message = "Adres nie może mieć więcej niż 50 znaków")
    private String city;

}
