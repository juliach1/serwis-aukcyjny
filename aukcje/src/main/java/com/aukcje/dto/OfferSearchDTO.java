package com.aukcje.dto;

import com.aukcje.enums.SortTypeEnum;
import com.aukcje.service.iface.CategoryService;
import com.aukcje.service.iface.OfferTypeService;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class OfferSearchDTO {

    //TODO: zmienić na private

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private OfferTypeService offerTypeService;
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)

    private CategoryService categoryService;
    List<String> allOfferTypes;
    Map<Integer,String> categoryIdNames;
    List<String> sortTypes;

    public OfferSearchDTO(OfferTypeService offerTypeService, CategoryService categoryService){
        this.offerTypeService = offerTypeService;
        this.categoryService = categoryService;
        allOfferTypes = new ArrayList<>();
        categoryIdNames = new HashMap<>();

        sortTypes = new ArrayList<>();
        sortTypes.add(SortTypeEnum.NEWEST.toString());
        sortTypes.add(SortTypeEnum.PRICE_ASCENDING.toString());
        sortTypes.add(SortTypeEnum.PRICE_DESCENDING.toString());
        sortTypes.add(SortTypeEnum.POPULARITY.toString());

        List<OfferTypeDTO> offerTypeDTOS = offerTypeService.findAll();
        for (OfferTypeDTO offerTypeDTO : offerTypeDTOS){
            allOfferTypes.add(offerTypeDTO.getName());
        }
        List<CategoryDTO> categoryDTOS = categoryService.getRootCategories();
        for(CategoryDTO categoryDTO : categoryDTOS){
            categoryIdNames.put(categoryDTO.getId(), categoryDTO.getName());
        }

        System.out.println("WYWOŁANO getOfferTypes()");
    }
}
