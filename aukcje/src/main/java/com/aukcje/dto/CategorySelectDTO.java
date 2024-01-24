package com.aukcje.dto;

import lombok.Data;

import java.util.List;

@Data
public class CategorySelectDTO {

    private int id;
    private String name;
    private CategorySelectDTO parentCategory;
    private List<CategorySelectDTO> subCategories;
    private int indent;

}
