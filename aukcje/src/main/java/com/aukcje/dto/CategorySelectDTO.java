package com.aukcje.dto;

import lombok.Data;

import java.util.List;

@Data
public class CategorySelectDTO {

    public int id;
    public String name;
    public CategorySelectDTO parentCategory;
    public List<CategorySelectDTO> subCategories;
    public int indent;
    //todo zmiany

}
