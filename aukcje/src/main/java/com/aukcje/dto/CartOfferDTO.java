package com.aukcje.dto;

import lombok.Data;

@Data
public class CartOfferDTO {

    public long id;

    public UserDTO userDTO;

    public OfferDTO offerDTO;
}
