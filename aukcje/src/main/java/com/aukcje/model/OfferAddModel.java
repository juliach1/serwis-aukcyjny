package com.aukcje.model;

import com.aukcje.validator.ValidNotZeroOrBlank;
import com.aukcje.validator.ValidPrice;
import lombok.Data;

import javax.validation.constraints.*;

@Data
public class OfferAddModel {

    private Long id;


    @Size(min=2, message = "Tytuł musi mieć przynajmniej 3 znaki!")
   @Size(max=80, message = "Tytuł nie może mieć więcej niż 80 znaków.")
   private String title;

   @Size(min=30, message = "Opis musi składać się z conajmniej 30 znaków")
   @Size(max=5000, message = "Opis oferty nie może mieć więcej niż 5000 znaków")
   private String description;

   private Integer categoryId;

   private Boolean isBaseCategoryChosen;

   private String offerType;

   @NotNull(message = "Ustaw stan przedmiotu.")
   private String itemCondition;


    @ValidNotZeroOrBlank
    @ValidPrice
    private String price;

    @NotNull(message = "Podaj liczbę sprzedawanych przedmiotów")
    @Min(value = 1, message = "Wprowadź prawidłową liczbę przedmiotów")
    private Integer quantity;

    @Min(value = 1, message = "Aukcja nie może trwać mniej niż 1 dzień")
    @Max(value = 90, message = "Maksymalna długość aukcji to 90 dni")
    private Integer lengthInDays;
}
