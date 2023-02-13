package com.aukcje.dto.mapper;

import com.aukcje.dto.CategoryParentHierarchyDTO;
import com.aukcje.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CategoryParentHierarchyDTOMapper {

    CategoryParentHierarchyDTOMapper instance = Mappers.getMapper(CategoryParentHierarchyDTOMapper.class);

    @Mapping(target = "nodes", ignore = true)
    @Mapping(target = "expanded", ignore = true)
    @Mapping(target = "href", ignore = true)
    CategoryParentHierarchyDTO categoryParentHierarchyDTO(Category category);


}
