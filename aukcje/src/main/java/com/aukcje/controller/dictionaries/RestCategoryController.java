package com.aukcje.controller.dictionaries;

import com.aukcje.dto.CategoryParentHierarchyDTO;
import com.aukcje.dto.CategorySelectionParentHierarchyDTO;
import com.aukcje.service.iface.CategoryService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rest")
public class RestCategoryController {

    @Autowired
    private CategoryService categoryService;

    @Setter
    private String baseUrl;


    @Transactional
    @GetMapping("/categories")
    public List<CategoryParentHierarchyDTO> getCategories(){
        System.out.println("rest url: "+ baseUrl);
        return categoryService.createCategoriesTree(baseUrl);
    }


    @Transactional
    @GetMapping("/categories/selection")
    public List<CategorySelectionParentHierarchyDTO> getSelectionCategories(){
        System.out.println("rest url: "+ baseUrl);
        return categoryService.createCategoriesSelectionTree(baseUrl);
    }



}
