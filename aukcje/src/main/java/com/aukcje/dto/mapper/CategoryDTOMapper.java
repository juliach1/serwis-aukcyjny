package com.aukcje.dto.mapper;

import com.aukcje.dto.CategoryDTO;
import com.aukcje.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CategoryDTOMapper {

    CategoryDTOMapper instance = Mappers.getMapper(CategoryDTOMapper.class);

    @Mapping(target = "parentCategory", ignore = true)
    @Mapping(target = "subCategories", ignore = true)
    CategoryDTO categoryDTO(Category category);
}
