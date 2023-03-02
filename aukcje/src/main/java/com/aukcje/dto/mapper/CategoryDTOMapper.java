package com.aukcje.dto.mapper;

import com.aukcje.dto.CategoryDTO;
import com.aukcje.dto.CategoryParentHierarchyDTO;
import com.aukcje.dto.CategoryPathCategoryDTO;
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

    @Mappings({})
    CategoryPathCategoryDTO categoryTreeCategoryDTO(Category category);

    @Mapping(target = "nodes", ignore = true)
    @Mapping(target = "expanded", ignore = true)
    @Mapping(target = "href", ignore = true)
    CategoryParentHierarchyDTO categoryParentHierarchyDTO(Category category);


}
