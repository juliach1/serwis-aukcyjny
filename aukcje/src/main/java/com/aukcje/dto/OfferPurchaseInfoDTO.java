package com.aukcje.dto;

import lombok.Data;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Data
public class OfferPurchaseInfoDTO {

    private UserDTO seller;
    private UserDTO buyer;
    private OfferDTO offer;
    private AddressDTO address;
    private PurchaseStatusDTO purchaseStatus;
    private LocalDateTime purchaseTime;
    private Double price;
    private Integer quantity;
}
