package com.aukcje.dto;

import com.aukcje.enums.SortTypeEnum;
import com.aukcje.service.OfferTypeServiceImpl;
import com.aukcje.service.iface.CategoryService;
import com.aukcje.service.iface.OfferTypeService;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;

@Data
public class OfferSearchDTO {

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
        sortTypes.add(SortTypeEnum.NAJNOWSZE.toString());
        sortTypes.add(SortTypeEnum.CENA_OD_NAJNIZSZEJ.toString());
        sortTypes.add(SortTypeEnum.CENA_OD_NAJWYZSZEJ.toString());
        sortTypes.add(SortTypeEnum.POPULARNOSC.toString());

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
