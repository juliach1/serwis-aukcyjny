package com.aukcje.service;

import com.aukcje.dto.CategoryDTO;
import com.aukcje.dto.CategoryParentHierarchyDTO;
import com.aukcje.dto.CategorySelectDTO;
import com.aukcje.dto.mapper.CategoryDTOMapper;
import com.aukcje.dto.mapper.CategoryParentHierarchyDTOMapper;
import com.aukcje.entity.Category;
import com.aukcje.model.CategoryModel;
import com.aukcje.model.mapper.CategoryAddModelMapper;
import com.aukcje.repository.CategoryRepository;
import com.aukcje.service.iface.CategoryService;
import lombok.Setter;
import org.apache.catalina.startup.WebAnnotationSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.endpoint.web.annotation.WebEndpoint;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.naming.Context;
import javax.servlet.ServletContext;
import javax.validation.constraints.AssertFalse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;


    @Override
    @Transactional
    public List<CategoryDTO> findAll() {
        List<Category> categoryList = categoryRepository.findAll();
        System.out.println(categoryList.get(0).getName());

        for(Category temp : categoryList){
            System.out.println("ID: " + temp.getId());
            System.out.println("Nazwa rodzica: " + (temp.getParentCategory() == null ? "Null" : temp.getParentCategory().getName()));
            System.out.println("Nazwa: "+ temp.getName());
        }
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
        Category category = CategoryAddModelMapper.instance.category(categoryModel);

        if(!categoryModel.getParentCategory().equals("placeholder")){
            Integer parentId = Integer.parseInt(categoryModel.getParentCategory());
            Category parentCategory = categoryRepository.findById(parentId).orElseThrow();

            category.setParentCategory(parentCategory);
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
    public List<CategoryParentHierarchyDTO> createCategoriesTree(String baseUrl) {
        List<Category> topCategories = categoryRepository.getCategoriesByParentCategoryIsNull();

        for(Category category : topCategories){
            category.setSubCategories(categoryRepository.getCategoriesByParentCategory(category));
        }

        return createCategoryParentHierarchy(topCategories, baseUrl);
    }


    private String createHrefForCategory(Category category, String baseUrl) {
        String href = baseUrl + "/edytuj/" + category.getId();
        System.out.println("href:"+href);

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
        System.out.println("RODZIC KATEGORII "+categoryDTO.getParentCategory());

        List<Category> subCategories = categoryRepository.getCategoriesByParentCategory(category);
        List<String> subCategoryNames = new ArrayList<>();
        System.out.println("DZIECI :");
        for (Category subCategory : subCategories){
            subCategoryNames.add(subCategory.getName());
            System.out.print(subCategory.getName()+", ");

        }
        categoryDTO.setSubCategories(subCategoryNames);


        return categoryDTO;
    }

    private List<CategoryDTO> createCategoryDTO(List<Category> categories){
        List<CategoryDTO> categoryDTOS = new ArrayList<>();

        for(Category category: categories){
            CategoryDTO categoryDTO = CategoryDTOMapper.instance.categoryDTO(category);
            Category parentCategory = categoryRepository.getParentCategoryBySubCategoryId(category.getId()).orElse(null);
            categoryDTO.setParentCategory(parentCategory != null ? parentCategory.getName() : null);

            List<Category> subCategories = categoryRepository.getCategoriesByParentCategory(category);
            List<String> subCategoryNames = new ArrayList<>();
            for (Category subCategory : subCategories){
                subCategoryNames.add(subCategory.getName());
            }
            categoryDTO.setSubCategories(subCategoryNames);

            categoryDTOS.add(categoryDTO);
        }
        return categoryDTOS;
    }

    //    public List<CategorySelectDTO> createCategoriesNormalTree(){
//        List<Category> topCategories = categoryRepository.getCategoriesByParentCategoryIsNull();
//
//        for(Category category : topCategories){
//            category.setSubCategories(categoryRepository.getCategoriesByParentCategory(category));
//        }
//
//        return  createCategorySelectDTOSFromList(topCategories);
//    }


//    @Transactional
//    public CategorySelectDTO createCategorySelectDTO(Category category) {
//        CategorySelectDTO categorySelectDTO = new CategorySelectDTO();
//
//        categorySelectDTO.setName(category.getName());
//        categorySelectDTO.setIndent(categorySelectDTO.getIndent() + 10);
//
//        return categorySelectDTO;
//        categorySelectDTO.setName(category.getName());
//        categorySelectDTO.setId(category.getId());
//
//        if(categorySelectDTO.getParentCategory() == null){
//            categorySelectDTO.setIndent(0);
//        }else{
//            categorySelectDTO.setIndent(categorySelectDTO.getParentCategory().getIndent()+10);
//        }
//        List<CategorySelectDTO> subCategories = new ArrayList<>();
//        for(Category subCategory : category.getSubCategories()){
//            CategorySelectDTO subCat = createCategorySelectDTO(subCategory);
//            subCat.setParentCategory(categorySelectDTO);
//
//            if(subCat.getParentCategory() == null){
//                subCat.setIndent(0);
//            }else{
//                subCat.setIndent(subCat.getParentCategory().getIndent()+10);
//            }
//
//            subCategories.add(subCat);
//            System.out.print(subCategory.getName() + ",");
//        }
//        categorySelectDTO.setSubCategories(subCategories);
//    }


//    @Transactional
//    public List<CategorySelectDTO> createCategorySelectDTOSFromList(List<Category> categories) {
//        List<CategorySelectDTO> categorySelectDTOs = new ArrayList<>();
//
//        for (Category category : categories) {
//            CategorySelectDTO categorySelectDTO = new CategorySelectDTO();
//
//            if(category.getParentCategory()!=null){
//                categorySelectDTO.setParentCategory(createCategorySelectDTO(category.getParentCategory()));
//                categorySelectDTO.setIndent(categorySelectDTO.getParentCategory().getIndent() + 10);
//            }else {
//                categorySelectDTO.setIndent(0);
//            }
//            categorySelectDTO.setName(category.getName());
//
//            List<CategorySelectDTO> subCategories = new ArrayList<>();
//            for (Category subcat : category.getSubCategories()){
//                subCategories.add(createCategorySelectDTO(subcat));
//            }
//            categorySelectDTO.setSubCategories(subCategories);
//            categorySelectDTOs.add(categorySelectDTO);
//
//
//            System.out.println("KATEGORIA: ");
//            System.out.println("rodzic: "+ (categorySelectDTO.getParentCategory() != null ? categorySelectDTO.getParentCategory().getName() : "null"));
//            System.out.println("nazwa: "+ (categorySelectDTO.getName()));
//            System.out.println("indent: "+ (categorySelectDTO.getIndent()));
//            System.out.println("");
//
//            for (CategorySelectDTO subcats : categorySelectDTO.getSubCategories()){
//                System.out.println("KATEGORIA: ");
//                System.out.println("rodzic: "+ (subcats.getParentCategory() != null ? subcats.getParentCategory().getName() : "null"));
//                System.out.println("nazwa: "+ (subcats.getName()));
//                System.out.println("indent: "+ (subcats.getIndent()));
//                System.out.println("");
//
//            }
//            categorySelectDTO.setSubCategories(subCategories);
//            categorySelectDTOs.add(categorySelectDTO);
//
//
//        }
//
//        return categorySelectDTOs;
//    }



//    private List<CategorySelectDTO> createCategorySelectDTOSFromList(List<Category> categories){
//        List<CategorySelectDTO> categoryDTO = new ArrayList<>();
//
//        for(Category category : categories){
//            CategorySelectDTO categorySelectDTO = new CategorySelectDTO();
//            categorySelectDTO.setName(category.getName());
//
//            categorySelectDTO.setSubCategories(createCategorySelectDTOSFromList( category.getSubCategories()) );
//            if(category.getParentCategory() != null){
//                CategorySelectDTO parentCategory = new CategorySelectDTO();
//
//                parentCategory.setIndent(categorySelectDTO.getSubCategories().size()>0? categorySelectDTO.getSubCategories().get(0).getIndent()-20 : 0);
//                parentCategory.setName(category.getParentCategory().getName());
//                categorySelectDTO.setIndent(parentCategory.getIndent() + 10);
//
//
//            }else {
//                categorySelectDTO.setIndent(0);
//            }
//
//            System.out.println("NAZWA: "+categorySelectDTO.getName());
//            System.out.println("INDENT: "+categorySelectDTO.getIndent());
//            System.out.println("RODZIC: "+ (categorySelectDTO.parentCategory != null ? categorySelectDTO.parentCategory.getName() : "null"));
//            System.out.println("INDENT rodzica: "+(categorySelectDTO.parentCategory != null ? categorySelectDTO.parentCategory.getIndent() : "null"));
//
//            categoryDTO.add(categorySelectDTO);
//        }
//
//        return categoryDTO;
//    }


}
