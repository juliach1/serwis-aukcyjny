package com.aukcje.dto;

import lombok.Data;

@Data
public class UserRatingDTO {
    private UserDTO user;
    private Long rating;
}
