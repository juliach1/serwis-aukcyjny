package com.aukcje.dto;

import lombok.Data;

@Data
public class OfferDetailsDTO {

    private Long id;
    private String title;
    private String description;
    private ItemConditionOfferPreviewDTO itemCondition;

}
