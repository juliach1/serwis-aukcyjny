package com.aukcje.dto.mapper;

import com.aukcje.dto.CountryDTO;
import com.aukcje.dto.ItemConditionDTO;
import com.aukcje.dto.ItemConditionOfferPreviewDTO;
import com.aukcje.entity.Country;
import com.aukcje.entity.ItemCondition;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ItemConditionDTOMapper {

    ItemConditionDTOMapper instance = Mappers.getMapper(ItemConditionDTOMapper.class);

    @Mappings({})
    ItemConditionOfferPreviewDTO itemConditionOfferPreviewDTO(ItemCondition itemCondition);

    @Mappings({})
    ItemConditionDTO itemConditionDTO(ItemCondition itemCondition);
}
