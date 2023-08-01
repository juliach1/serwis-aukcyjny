package com.aukcje.dto;

import lombok.Data;

@Data
public class OfferDetailsDTO {

    //TODO: zmienić na private

    public Long id;
    public String title;
    public String description;

    public ItemConditionOfferPreviewDTO itemCondition;
}
