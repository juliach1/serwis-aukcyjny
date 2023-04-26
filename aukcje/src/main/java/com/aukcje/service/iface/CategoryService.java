package com.aukcje.service.iface;

import com.aukcje.dto.*;
import com.aukcje.entity.Category;
import com.aukcje.model.CategoryModel;
import java.util.List;

public interface CategoryService {

    List<CategoryPathCategoryDTO> getCategoryPath(Category category);

    List<CategoryDTO> findAll();

    List<CategoryDTO> findAllExcept(Integer id);

    CategoryDTO findById(Integer id);

    void save(CategoryModel categoryModel);

    void update(CategoryModel categoryModel);

    void delete(Integer id);

    boolean categoryAlreadyExists(String name);

    List<CategoryParentHierarchyDTO> createCategoriesTree(String baseUrl);
     List<CategorySelectionParentHierarchyDTO> createCategoriesSelectionTree(String baseUrl);
        List<CategoryDTO> getRootCategories();

//    List<CategorySelectDTO> createCategoriesNormalTree();


//     CategorySelectDTO  createCategorySelectDTO(Category category);









}
