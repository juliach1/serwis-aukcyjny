package com.aukcje.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class CategoryParentHierarchyDTO {

//    int id;

    String text;
    List<CategoryParentHierarchyDTO> nodes;
    Boolean expanded = true;
    String href;

    @JsonProperty("class")
    String classField;

}
