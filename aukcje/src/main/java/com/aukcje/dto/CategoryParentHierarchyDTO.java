package com.aukcje.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class CategoryParentHierarchyDTO {

    private String text;
    private List<CategoryParentHierarchyDTO> nodes;
    private Boolean expanded = true;
    private String href;

    @JsonProperty("class")
    private String classField;

}
