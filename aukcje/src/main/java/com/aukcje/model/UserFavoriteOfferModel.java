package com.aukcje.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserFavoriteOfferModel {

    private Long id;
    private Long userId;
    private Long offerId;
    private LocalDateTime insertTime;
}
