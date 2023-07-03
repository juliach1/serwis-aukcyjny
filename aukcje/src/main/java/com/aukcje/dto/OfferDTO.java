package com.aukcje.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OfferDTO {
    public long id;
    public CategoryDTO categoryDTO;
    public OfferDetailsDTO offerDetails;
    public OfferTypeDTO offerType;
    public UserDTO user;
    public Double price;
    public Long views;
    public Integer quantity;
    public LocalDateTime insertDate;
    public Integer daysLeft;
    public LocalDateTime endDate;
    public OfferStatusDTO offerStatusDTO;
    public List<OfferPhotoDTO> offerPhotoDTO;

    public List<CategoryPathCategoryDTO> categoryPath;


}
