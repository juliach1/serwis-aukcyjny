package com.aukcje.dto.mapper;

import com.aukcje.dto.CategoryDTO;
import com.aukcje.dto.CategoryPathCategoryDTO;
import com.aukcje.dto.OfferDTO;
import com.aukcje.dto.OfferStatusDTO;
import com.aukcje.entity.Category;
import com.aukcje.entity.Offer;
import com.aukcje.entity.OfferStatus;
import com.aukcje.service.CategoryServiceImpl;
import com.aukcje.service.iface.CategoryService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper
public interface OfferDTOMapper {

    OfferDTOMapper instance = Mappers.getMapper(OfferDTOMapper.class);

    CategoryService categoryService = new CategoryServiceImpl();

    @Mapping(target = "daysLeft", ignore = true)
    @Mapping(target = "isFavorite", ignore = true)
    @Mapping(target = "categoryPath", source = "category", qualifiedByName = "categoryPath")
    OfferDTO offerDTO(Offer offer);

    default CategoryDTO map(Category category){
        return CategoryDTOMapper.instance.categoryDTO(category);
    }

    default OfferStatusDTO map(OfferStatus offerStatus){ return OfferStatusDTOMapper.instance.offerStatusDTO(offerStatus); }

    @Named("categoryPath")
    static List<CategoryPathCategoryDTO> mapCategoryPath(Category category){
        return categoryService.getCategoryPath(category);
    }

}
