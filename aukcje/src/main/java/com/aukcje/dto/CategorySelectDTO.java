package com.aukcje.dto;

import lombok.Data;

import java.util.List;

@Data
public class CategorySelectDTO {

    //TODO: zmienić na private

    public int id;
    public String name;
    public CategorySelectDTO parentCategory;
    public List<CategorySelectDTO> subCategories;
    public int indent;
    //todo zmiany

}
