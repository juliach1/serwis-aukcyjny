package com.aukcje.model;

import com.aukcje.validator.RangeValidator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@RangeValidator(message = "Cena minimalna nie może być wyższa od ceny minimalnej...")
public class OfferSearchModel {

    private List<String> offerTypes;

    private Boolean searchInDescription;

    private String phrase;

    private Integer searchCategory;
//
//    @NotNull
//    private String sortType;

    @Min(value = 0, message = "Cena nie może być niższa niż 0zł")
    @Max(value = 1000000000, message = "Cena jest za wysoka")
    private Double minPrice;

    @Min(value = 0, message = "Cena nie może być niższa niż 0zł")
    @Max(value = 1000000000, message = "Cena jest za wysoka")
    private Double maxPrice;

    private boolean minSmallerThanMax;

    private Integer pageSize;

    @AssertTrue(message = "Cena minimalna nie może być wyższa od ceny minimalnej...")
    public boolean isMinSmallerThanMax(){
        if(minPrice == null || maxPrice == null){
            return true;
        }
        return minPrice <= maxPrice;
    }
}
