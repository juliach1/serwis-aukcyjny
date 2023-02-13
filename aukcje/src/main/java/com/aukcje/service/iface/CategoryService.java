package com.aukcje.service.iface;

import com.aukcje.dto.CategoryDTO;
import com.aukcje.dto.CategoryParentHierarchyDTO;
import com.aukcje.dto.CategorySelectDTO;
import com.aukcje.entity.Category;
import com.aukcje.model.CategoryModel;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface CategoryService {


    List<CategoryDTO> findAll();

    List<CategoryDTO> findAllExcept(Integer id);

    CategoryDTO findById(Integer id);

    void save(CategoryModel categoryModel);

    void update(CategoryModel categoryModel);

    void delete(Integer id);

    boolean categoryAlreadyExists(String name);

    List<CategoryParentHierarchyDTO> createCategoriesTree(String baseUrl);

    List<CategoryDTO> getRootCategories();

//    List<CategorySelectDTO> createCategoriesNormalTree();


//     CategorySelectDTO  createCategorySelectDTO(Category category);









}
