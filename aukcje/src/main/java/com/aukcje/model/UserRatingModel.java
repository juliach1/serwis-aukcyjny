package com.aukcje.model;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
public class UserRatingModel {

    private Long userId;

    private Long purchaseId;

    @Min(1)
    @Max(5)
    private Integer rating;
}
