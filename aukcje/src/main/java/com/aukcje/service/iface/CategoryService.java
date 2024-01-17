package com.aukcje.service.iface;

import com.aukcje.dto.CategoryDTO;
import com.aukcje.dto.CategoryParentHierarchyDTO;
import com.aukcje.dto.CategoryPathCategoryDTO;
import com.aukcje.dto.CategorySelectionParentHierarchyDTO;
import com.aukcje.entity.Category;
import com.aukcje.exception.customException.NoSuchCategoryException;
import com.aukcje.model.CategoryModel;
import com.aukcje.model.OfferAddModel;

import java.util.List;

public interface CategoryService {

    Boolean isChosenCategoryForOfferAddCorrect(OfferAddModel offerAddModel);

    List<CategoryPathCategoryDTO> getCategoryPath(Category category);

    List<CategoryDTO> findAll();

    List<CategoryDTO> findAllExcept(Integer id);

    CategoryDTO findById(Integer id);

    CategoryDTO findById(String categoryName);

    void save(CategoryModel categoryModel);

    void update(CategoryModel categoryModel);

    void delete(Integer id) throws NoSuchCategoryException;

    boolean categoryAlreadyExists(String name);

    List<CategoryParentHierarchyDTO> createCategoriesTree(String baseUrl);

    List<CategorySelectionParentHierarchyDTO> createCategoriesSelectionTree(String baseUrl);

    List<CategoryDTO> getRootCategories();
}
