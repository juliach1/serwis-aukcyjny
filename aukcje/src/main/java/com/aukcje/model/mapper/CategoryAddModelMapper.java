package com.aukcje.model.mapper;

import com.aukcje.entity.Category;
import com.aukcje.model.CategoryModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CategoryAddModelMapper {

    CategoryAddModelMapper instance = Mappers.getMapper(CategoryAddModelMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "parentCategory", ignore = true)
    Category category(CategoryModel categoryModel);

}