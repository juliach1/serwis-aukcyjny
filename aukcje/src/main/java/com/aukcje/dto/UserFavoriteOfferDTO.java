package com.aukcje.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserFavoriteOfferDTO {

    private Long id;
    private UserDTO user;
    private OfferDTO offer;
    private LocalDateTime insertTime;

}
