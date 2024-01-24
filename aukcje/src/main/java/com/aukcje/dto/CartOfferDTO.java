package com.aukcje.dto;

import lombok.Data;

@Data
public class CartOfferDTO {

    private Long id;
    private UserDTO user;
    private OfferDTO offer;
    private Integer quantity;

}
