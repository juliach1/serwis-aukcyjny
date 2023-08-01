package com.aukcje.dto;

import lombok.Data;

@Data
public class CartOfferDTO {

    //TODO: zmienić na private

    public long id;

    public UserDTO user;

    public OfferDTO offer;
}
