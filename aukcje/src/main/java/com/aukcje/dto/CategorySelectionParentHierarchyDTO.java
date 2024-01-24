package com.aukcje.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
@Data
public class CategorySelectionParentHierarchyDTO {

    private Integer id;
    private String text;
    private List<CategorySelectionParentHierarchyDTO> nodes;
    private Boolean expanded = true;
    private Boolean selectable = false;
    private Boolean enabled = true;
    private CategorySelectionParentHierarchyStateDTO state = new CategorySelectionParentHierarchyStateDTO();

    @JsonProperty("class")
    String classField;

}
