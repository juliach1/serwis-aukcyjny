package com.aukcje.dto;

import lombok.Data;

@Data
public class OfferPhotoDTO {
    public OfferDTO offer;
    public String path;
    public Integer sequence;
}
