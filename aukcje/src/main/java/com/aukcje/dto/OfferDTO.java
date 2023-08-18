package com.aukcje.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OfferDTO {

    //TODO: zmienić na private

    public Long id;
    public CategoryDTO category;
    public OfferDetailsDTO offerDetails;
    public OfferTypeDTO offerType;
    public UserDTO user;
    public Double price;
    public Long views;
    public Integer quantity;
    public LocalDateTime insertDate;
    public Integer daysLeft;
    public LocalDateTime endDate;
    public OfferStatusDTO offerStatus;
    public List<OfferPhotoDTO> offerPhoto;

    public List<CategoryPathCategoryDTO> categoryPath;


}
