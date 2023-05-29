package com.aukcje.dto;

import lombok.Data;

@Data
public class OfferPhotoDTO {
    private OfferDTO offer;
    private String path;
    private Integer sequence;
}
