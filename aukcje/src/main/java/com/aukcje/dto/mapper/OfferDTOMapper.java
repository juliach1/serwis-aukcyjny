package com.aukcje.dto.mapper;

import com.aukcje.dto.CategoryDTO;
import com.aukcje.dto.OfferDTO;
import com.aukcje.entity.Category;
import com.aukcje.entity.Offer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OfferDTOMapper {

    OfferDTOMapper instance = Mappers.getMapper(OfferDTOMapper.class);


    @Mapping(target = "daysLeft", ignore = true)
    OfferDTO offerDTO(Offer offer);

    default CategoryDTO map(Category category){
        return CategoryDTOMapper.instance.categoryDTO(category);
    }

}
