package com.aukcje.service;

import com.aukcje.dto.CategoryDTO;
import com.aukcje.dto.CategoryParentHierarchyDTO;
import com.aukcje.dto.CategoryPathCategoryDTO;
import com.aukcje.dto.CategorySelectionParentHierarchyDTO;
import com.aukcje.dto.mapper.CategoryDTOMapper;
import com.aukcje.entity.Category;
import com.aukcje.model.CategoryModel;
import com.aukcje.model.OfferAddModel;
import com.aukcje.model.mapper.CategoryAddModelMapper;
import com.aukcje.repository.CategoryRepository;
import com.aukcje.service.iface.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.AssertFalse;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Transactional
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;


    @Override
    public Boolean isChosenCategoryForOfferAddCorrect(OfferAddModel offerAddModel) {

        //       boolean var1 = anyCategoryChosen(offerAddModel.getCategoryId());
//        boolean var2 = baseCategoryChosen(offerAddModel.getIsBaseCategoryChosen());
        return anyCategoryChosen(offerAddModel.getCategoryId()) && baseCategoryChosen(offerAddModel.getIsBaseCategoryChosen());
    }


    private Boolean baseCategoryChosen(Boolean baseCatChosen){
       return  !Objects.isNull(baseCatChosen) && baseCatChosen;
    }
    private Boolean categoryExist(Integer id){
        return categoryRepository.getById(id).isPresent();
    }

    private Boolean anyCategoryChosen(Integer id){
        return Objects.nonNull(id);
    }

    @Override
    public List<CategoryPathCategoryDTO> getCategoryPath(Category category) {
        List<CategoryPathCategoryDTO> categoryPathCategoryDTOS = new LinkedList<>();

        categoryPathCategoryDTOS.add(CategoryDTOMapper.instance.categoryTreeCategoryDTO(category));

        while (category.getParentCategory() != null) {
            category = category.getParentCategory();
            categoryPathCategoryDTOS.add(CategoryDTOMapper.instance.categoryTreeCategoryDTO(category));
        }

        List<CategoryPathCategoryDTO> revList = new LinkedList<>();
        for(int i=categoryPathCategoryDTOS.size()-1; i>=0; i--){
            revList.add(categoryPathCategoryDTOS.get(i));
        }

        return revList;
    }

    @Override
    @Transactional
    public List<CategoryDTO> findAll() {
        return createCategoryDTO(categoryRepository.findAll());
    }

    @Override
    public List<CategoryDTO> findAllExcept(Integer id) {
        return createCategoryDTO(categoryRepository.findByIdNot(id));
    }

    @Transactional
    @Override
    public CategoryDTO findById(Integer id) {
        Category category = categoryRepository.findById(id).orElse(null);
        CategoryDTO categoryDTO = createCategoryDTO(category);
        if(category.getParentCategory() != null){
            categoryDTO.setParentCategory(category.getParentCategory().getName());
        }

        return categoryDTO;
    }

    @Override
    public void save(CategoryModel categoryModel) {
        Category category = CategoryAddModelMapper.instance.category(categoryModel);


        if(!categoryAlreadyExists(categoryModel.getName())){
            if(!categoryModel.getParentCategory().equals("placeholder")){
                Integer parentId = Integer.parseInt(categoryModel.getParentCategory());
                Category parentCategory = categoryRepository.findById(parentId).orElseThrow();

                category.setParentCategory(parentCategory);
            }
            categoryRepository.save(category);
        }

    }

    @Override
    public void update(CategoryModel categoryModel) {
        Category category = categoryRepository.findByName(categoryModel.getName());

        if(!categoryModel.getParentCategory().equals("placeholder")){
            Integer parentId = Integer.parseInt(categoryModel.getParentCategory());
            Category parentCategory = categoryRepository.findById(parentId).orElseThrow();

            category.setParentCategory(parentCategory);
        }else {
            category.setParentCategory(null);
        }
        categoryRepository.save(category);
    }

    @Override
    public void delete(Integer id){
        categoryRepository.delete(categoryRepository.findById(id).orElseThrow());
    }

    @AssertFalse(message = "Nazwa kategorii jest zajęta!")
    public boolean categoryAlreadyExists(String name){

        List<CategoryDTO> allCategories = this.findAll();
        for(CategoryDTO categoryDTO : allCategories){
            if(categoryDTO.getName().equalsIgnoreCase(name)){
                return true;
            }
        }
        return false;
    }

    public List<CategoryDTO> getRootCategories(){
        return createCategoryDTO(categoryRepository.getCategoriesByParentCategoryIsNull());
    }

    @Override
    public CategoryDTO findById(String id) {
        return findById(Integer.parseInt(id));
    }

    @Override
    public List<CategoryParentHierarchyDTO> createCategoriesTree(String baseUrl) {
        List<Category> topCategories = categoryRepository.getCategoriesByParentCategoryIsNull();

        for(Category category : topCategories){
            category.setSubCategories(categoryRepository.getCategoriesByParentCategory(category));
        }

        return createCategoryParentHierarchy(topCategories, baseUrl);
    }

    @Override
    public List<CategorySelectionParentHierarchyDTO> createCategoriesSelectionTree(String baseUrl) {
        List<Category> topCategories = categoryRepository.getCategoriesByParentCategoryIsNull();

        for(Category category : topCategories){
            category.setSubCategories(categoryRepository.getCategoriesByParentCategory(category));
        }

        return createCategorySelectionParentHierarchy(topCategories);
    }


    private List<CategorySelectionParentHierarchyDTO> createCategorySelectionParentHierarchy(List<Category> categories){
        List<CategorySelectionParentHierarchyDTO> categoryDTOS = new ArrayList<>();

        for(Category category: categories){
            CategorySelectionParentHierarchyDTO categoryParentHierarchyDTO = new CategorySelectionParentHierarchyDTO();

            categoryParentHierarchyDTO.setId(category.getId());
            categoryParentHierarchyDTO.setText(category.getName());
            categoryParentHierarchyDTO.setNodes(createCategorySelectionParentHierarchy((category.getSubCategories())));

            if(category.getParentCategory()==null) categoryParentHierarchyDTO.setClassField("fw-bold");

//            if(category.getSubCategories().isEmpty()) categoryParentHierarchyDTO.setSelectable(true);
            categoryDTOS.add(categoryParentHierarchyDTO);
        }

        return categoryDTOS;
    }


    private String createHrefForCategory(Category category, String baseUrl) {
        String href = baseUrl + "/edytuj/" + category.getId();

        return href;
    }

    private List<CategoryParentHierarchyDTO> createCategoryParentHierarchy(List<Category> categories, String baseHref){
        List<CategoryParentHierarchyDTO> categoryDTOS = new ArrayList<>();

        for(Category category: categories){
            CategoryParentHierarchyDTO categoryParentHierarchyDTO = new CategoryParentHierarchyDTO();
            categoryParentHierarchyDTO.setText(category.getName());
            categoryParentHierarchyDTO.setHref(createHrefForCategory(category, baseHref));
            categoryParentHierarchyDTO.setNodes(createCategoryParentHierarchy(category.getSubCategories(), baseHref));
            if(category.getParentCategory()==null) categoryParentHierarchyDTO.setClassField("fw-bold");

            categoryDTOS.add(categoryParentHierarchyDTO);
        }

        return categoryDTOS;
    }

    private CategoryDTO createCategoryDTO(Category category){
        CategoryDTO categoryDTO = CategoryDTOMapper.instance.categoryDTO(category);
        Category parentCategory = categoryRepository.getParentCategoryBySubCategoryId(category.getId()).orElse(null);
        categoryDTO.setParentCategory((parentCategory != null) ? parentCategory.getName() : null);

        List<Category> subCategories = categoryRepository.getCategoriesByParentCategory(category);
        List<String> subCategoryNames = new ArrayList<>();
        for (Category subCategory : subCategories){
            subCategoryNames.add(subCategory.getName());
        }
        categoryDTO.setSubCategories(subCategoryNames);


        return categoryDTO;
    }

    private List<CategoryDTO> createCategoryDTO(List<Category> categories){
        List<CategoryDTO> categoryDTOS = new ArrayList<>();

        for(Category category: categories){
            CategoryDTO categoryDTO = createCategoryDTO(category);
            categoryDTOS.add(categoryDTO);
        }
        return categoryDTOS;
    }


}
