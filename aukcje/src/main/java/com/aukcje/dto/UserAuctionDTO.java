package com.aukcje.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserAuctionDTO {
    private double value;
    private LocalDateTime insertTime;
}
