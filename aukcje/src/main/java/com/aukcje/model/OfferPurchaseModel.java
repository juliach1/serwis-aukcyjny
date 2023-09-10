package com.aukcje.model;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class OfferPurchaseModel {

    @NotNull
    private Long offerId;

    @NotNull
    @Min(1)
    private Integer quantity;

    @NotNull
    private Double price;
}
