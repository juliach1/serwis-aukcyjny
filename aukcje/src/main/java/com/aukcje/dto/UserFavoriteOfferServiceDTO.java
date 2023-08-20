package com.aukcje.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserFavoriteOfferServiceDTO {
    private Long id;
    private UserDTO user;
    private OfferDTO offer;
    private LocalDateTime localDateTime;
}
