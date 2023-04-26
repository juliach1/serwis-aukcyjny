package com.aukcje.model;

import lombok.Data;

import javax.validation.constraints.*;

@Data
public class OfferAddModel {

   private Long id;

   @NotEmpty(message = "Podaj tytuł")
   @Size(min=2, message = "Tytuł musi mieć przynajmniej 3 znaki!")
   @Size(max=80, message = "Tytuł nie może mieć więcej niż 80 znaków.")
   private String title;

   @NotEmpty(message = "Dodaj opis oferty!")
   @Size(min=30, message = "Opis musi składać się z conajmniej 30 znaków")
   @Size(max=80, message = "Opis oferty nie może mieć więcej niż 5000 znaków")
   private String description;

   private Integer categoryId;

   private String offerType;

   private String itemCondition;


    @Digits(integer = 6, fraction = 2, message = "Podano nieprawidłową cenę")
    @DecimalMin(value = "0.01", inclusive = false, message = "Cena musi być większa od 0")
    private Double price;

    @Min(value = 1, message = "Wprowadź prawidłową liczbę przedmiotów")
    private Integer quantity;
}
