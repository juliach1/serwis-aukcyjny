package com.aukcje.dto;

import lombok.Data;

import java.util.List;

@Data
public class CategoryDTO {

    //TODO: zmienić na private

    public int id;
    public String name;
    public String parentCategory;

    public List<String> subCategories;

}
