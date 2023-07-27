package com.aukcje.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class CartOfferModel {

    @NotNull
    private Long offerId;

    private Long userId;

    private Integer quantity;
}
