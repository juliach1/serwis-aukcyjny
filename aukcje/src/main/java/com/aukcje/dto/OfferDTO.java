package com.aukcje.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data

public class OfferDTO {
    public long id;
    public OfferDetailsDTO offerDetails;
    public OfferTypeDTO offerType;
    public UserDTO user;
    public Double price;
    public Long views;
    public Integer quantity;
    public LocalDateTime insertDate;
    public OfferStatusDTO offerStatusDTO;
    public List<OfferPhotoDTO> offerPhotoDTO;

    public List<CategoryPathCategoryDTO> categoryPath;


//    @Override
//    public String toString() {
//        return "OfferDTO{" +
//                "id=" + id +
//                ", offerDetails=" + offerDetails +
//                ", offerType=" + offerType +
//                ", user=" + user +
//                ", parentCategory=" + parentCategory +
//                ", price=" + price +
//                ", views=" + views +
//                ", quantity=" + quantity +
//                ", insertDate=" + insertDate +
//                ", offerStatusDTO=" + offerStatusDTO +
//                '}';
//    }
}
