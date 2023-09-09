package com.aukcje.controller.dictionaries;

import com.aukcje.dto.CategoryDTO;
import com.aukcje.exception.customException.NoSuchCategoryException;
import com.aukcje.exception.customException.IncorrectParentException;
import com.aukcje.model.CategoryModel;
import com.aukcje.service.iface.CategoryService;
import com.aukcje.service.iface.UserService;
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
import java.security.Principal;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/admin/kategoria")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;


    @Autowired
    private CategoryRestController categoryRestController;


    @GetMapping("/pobierz-wszystkie")
    @Transactional
    public String getCategories(HttpServletRequest httpServletRequest){
        List<CategoryDTO> categoryList = categoryService.findAll();
        String url = StringUtils.remove(httpServletRequest.getRequestURL().toString(), "/pobierz-wszystkie");
        categoryRestController.setBaseUrl(url);

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
                                     Errors error) throws IncorrectParentException {
        if(bindingResult.hasErrors() || categoryService.categoryAlreadyExists(categoryModel.getName())){
            if(categoryService.categoryAlreadyExists(categoryModel.getName())){
                error.reject("categoryExists","Kategoria o podanej nazwie już istnieje");
            }
            model.addAttribute("categoryModel", categoryModel);
            model.addAttribute("categories", categoryService.findAll());
            return "redirect:/admin/kategoria/pobierz-wszystkie";
        }

        CategoryDTO parentCategoryDTO;
        if (Objects.equals(categoryModel.getParentCategory(), "placeholder")){
            categoryService.update(categoryModel);
        }else {
            parentCategoryDTO = categoryService.findById(categoryModel.getParentCategory());
            if(parentCategoryDTO != null)
                categoryService.update(categoryModel);
            else throw new IncorrectParentException();
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
                                      BindingResult bindingResult) throws IncorrectParentException {
        if(bindingResult.hasErrors()){
            model.addAttribute("categoryModel", categoryModel);
            model.addAttribute("categoryDTO", new CategoryModel());
            model.addAttribute("categories",  categoryService.findAllExcept(categoryModel.getId()));

            return "/views/admin/category/categoryedit";
        }

        CategoryDTO parentCategoryDTO;
        if (Objects.equals(categoryModel.getParentCategory(), "placeholder")){
            categoryService.update(categoryModel);
        }else {
            parentCategoryDTO = categoryService.findById(categoryModel.getParentCategory());
            if(parentCategoryDTO != null)
                categoryService.update(categoryModel);
            else throw new IncorrectParentException();
        }

        return "redirect:/admin/kategoria/pobierz-wszystkie";
    }

    @GetMapping("/usun/{kategoriaId}")
    public String deleteCategory(Principal principal,
                                @PathVariable("kategoriaId") Integer categoryId) throws NoSuchCategoryException {

        CategoryDTO categoryDTO = categoryService.findById(categoryId);
        if(categoryDTO!=null){
            //TODO dodać: CZY NA PEWNO USUNĄĆ?
            categoryService.delete(categoryId);
        }else {

            //TODO: [pytanie] czy powinnam robic to tutaj, czy w serwisie?
            //tzn sprawdzać czy jest taka kategoria

            throw new NoSuchCategoryException();
        }

        return "redirect:/admin/kategoria/pobierz-wszystkie";
    }
}

