package com.aukcje.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class CategoryModel {

    //TODO: zmienić na private

    private int id;

    @Size(max = 50, message = "Nazwa kategorii może mieć maksymalnie 50 znaków")
    @NotBlank(message = "Nazwa nie może być pusta!")
    private String name;

    private String parentCategory;
}
