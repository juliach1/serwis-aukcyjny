package com.aukcje.dto;

import lombok.Data;

import java.util.List;

@Data
public class CategoryDTO {

    private int id;
    private String name;
    private String parentCategory;
    private List<String> subCategories;

}
