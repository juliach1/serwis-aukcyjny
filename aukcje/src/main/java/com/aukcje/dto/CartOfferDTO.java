package com.aukcje.dto;

import lombok.Data;

@Data
public class CartOfferDTO {

    //TODO: zmienić na private

    public Long id;

    public UserDTO user;

    public OfferDTO offer;

    public Integer quantity;
}
