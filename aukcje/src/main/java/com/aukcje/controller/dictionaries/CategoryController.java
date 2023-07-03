package com.aukcje.controller.dictionaries;

import com.aukcje.dto.CategoryDTO;
import com.aukcje.model.CategoryModel;
import com.aukcje.service.iface.CategoryService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/admin/kategoria")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private RestCategoryController restCategoryController;


    @GetMapping("/pobierz-wszystkie")
    @Transactional
    public String getCategories(HttpServletRequest httpServletRequest){
        List<CategoryDTO> categoryList = categoryService.findAll();
        String url = StringUtils.remove(httpServletRequest.getRequestURL().toString(), "/pobierz-wszystkie");
        restCategoryController.setBaseUrl(url);

        System.out.println(categoryList);
        return "/views/admin/category/category";
    }

    @GetMapping("/dodaj")
    public String addCategory(Model model){
        model.addAttribute("categoryModel", new CategoryModel());
        model.addAttribute("categories", categoryService.findAll());

        return "/views/admin/category/categoryadd";
    }

    @PostMapping("/dodaj/przetworz")
    public String addCategoryProcess(Model model,
                                     @Valid @ModelAttribute("categoryModel") CategoryModel categoryModel,
                                     BindingResult bindingResult,
                                     Errors error){
        if(bindingResult.hasErrors() || categoryService.categoryAlreadyExists(categoryModel.getName())){
            if(categoryService.categoryAlreadyExists(categoryModel.getName())){
                error.reject("categoryExists","Kategoria o podanej nazwie już istnieje");
            }
            model.addAttribute("categoryModel", categoryModel);
            model.addAttribute("categories", categoryService.findAll());

            return "redirect:/admin/kategoria/pobierz-wszystkie";

        }

        if(!categoryService.categoryAlreadyExists(categoryModel.getName())){
            categoryService.save(categoryModel);
        }

        return "redirect:/admin/kategoria/pobierz-wszystkie";
    }

    @GetMapping("/edytuj/{kategoriaId}")
    public String editCategory(@PathVariable("kategoriaId") Integer categoryId,
                               Model model){

        model.addAttribute("categoryDTO", categoryService.findById(categoryId));
        model.addAttribute("categoryModel", new CategoryModel());
        model.addAttribute("categories", categoryService.findAllExcept(categoryId));

        return "/views/admin/category/categoryedit";

    }

    @PostMapping("/edytuj/przetworz")
    public String editCategoryProcess(Model model,
                                      @Valid @ModelAttribute("categoryModel") CategoryModel categoryModel,
                                      BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            model.addAttribute("categoryModel", categoryModel);
            model.addAttribute("categoryDTO", new CategoryModel());
            model.addAttribute("categories",  categoryService.findAllExcept(categoryModel.getId()));

            return "/views/admin/category/categoryedit";
        }

        categoryService.update(categoryModel);
        return "redirect:/admin/kategoria/pobierz-wszystkie";
    }

    @GetMapping("/usun/{kategoriaId}")
    public String deleteCategory(@PathVariable("kategoriaId") Integer categoryId){

        categoryService.delete(categoryId);
        return "redirect:/admin/kategoria/pobierz-wszystkie";
    }
}

