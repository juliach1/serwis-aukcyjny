package com.aukcje.dto;

import lombok.Data;

@Data
public class OfferDetailsDTO {

    public Long id;
    public String title;
    public String description;

    public ItemConditionOfferPreviewDTO itemCondition;
}
