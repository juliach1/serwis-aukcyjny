package com.aukcje.dto.mapper;

import com.aukcje.dto.CartOfferDTO;
import com.aukcje.dto.CategoryDTO;
import com.aukcje.entity.CartOffer;
import com.aukcje.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CartOfferDTOMapper {

    CartOfferDTOMapper instance = Mappers.getMapper(CartOfferDTOMapper.class);

    @Mappings({})
    CartOfferDTO cartOfferDTO(CartOffer cartOffer);

    default CategoryDTO map(Category category){
        return CategoryDTOMapper.instance.categoryDTO(category);
    }
}
