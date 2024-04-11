package com.aukcje.controller.dictionaries;

import com.aukcje.dto.CategoryParentHierarchyDTO;
import com.aukcje.dto.CategorySelectionParentHierarchyDTO;
import com.aukcje.service.iface.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Inject))
@RequestMapping("/rest")
public class CategoryRestController {

    private final CategoryService categoryService;

    @Setter
    private String baseUrl;

    @Transactional
    @GetMapping("/categories")
    public List<CategoryParentHierarchyDTO> getCategories(){
        return categoryService.createCategoriesTree(baseUrl);
    }

    @Transactional
    @GetMapping("/categories/selection")
    public List<CategorySelectionParentHierarchyDTO> getSelectionCategories(){
        return categoryService.createCategoriesSelectionTree(baseUrl);
    }

}
