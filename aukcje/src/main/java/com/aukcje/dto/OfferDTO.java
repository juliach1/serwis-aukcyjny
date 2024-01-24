package com.aukcje.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OfferDTO {

    private Long id;
    private CategoryDTO category;
    private OfferDetailsDTO offerDetails;
    private OfferTypeDTO offerType;
    private UserDTO user;
    private Double price;
    private Long views;
    private Integer quantity;
    private LocalDateTime insertDate;
    private Integer daysLeft;
    private LocalDateTime endDate;
    private OfferStatusDTO offerStatus;
    private List<OfferPhotoDTO> offerPhoto;
    private List<CategoryPathCategoryDTO> categoryPath;
    private Boolean isFavorite;

}
