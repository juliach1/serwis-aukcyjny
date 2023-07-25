package com.aukcje.dto;

import lombok.Data;

@Data
public class CartOfferDTO {

    public long id;

    public UserDTO user;

    public OfferDTO offer;
}
