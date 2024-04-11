package com.aukcje.controller.dictionaries;

import com.aukcje.dto.CategoryDTO;
import com.aukcje.exception.customException.*;
import com.aukcje.model.CategoryModel;
import com.aukcje.service.iface.CategoryService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Objects;

@RequiredArgsConstructor(onConstructor = @__(@Inject))
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
@Controller
@RequestMapping("/admin/kategoria")
public class CategoryController {

    private final CategoryService categoryService;
    private final CategoryRestController categoryRestController;

    @GetMapping("/pobierz-wszystkie")
    @Transactional
    public String getCategories(HttpServletRequest httpServletRequest) {
        String url = StringUtils.remove(httpServletRequest.getRequestURL().toString(), "/pobierz-wszystkie");
        categoryRestController.setBaseUrl(url);

        return "/views/admin/category/category";
    }

    @GetMapping("/dodaj")
    public String addCategory(Model model) {
        model.addAttribute("categoryModel", new CategoryModel());
        model.addAttribute("categories", categoryService.findAll());

        return "/views/admin/category/category-add";
    }

    @PostMapping("/dodaj/przetworz")
    public String addCategoryProcess(Model model,
                                     @Valid @ModelAttribute("categoryModel") CategoryModel categoryModel,
                                     BindingResult bindingResult,
                                     Errors error) throws IncorrectParentException {
        if(bindingResult.hasErrors() || categoryService.categoryAlreadyExists(categoryModel.getName())) {
            if(categoryService.categoryAlreadyExists(categoryModel.getName()))
                error.reject("categoryExists","Kategoria o podanej nazwie już istnieje");

            model.addAttribute("categoryModel", categoryModel);
            model.addAttribute("categories", categoryService.findAll());

            return "redirect:/admin/kategoria/pobierz-wszystkie";
        }

        categoryService.update(categoryModel);
//        CategoryDTO parentCategoryDTO;
//        if (Objects.equals(categoryModel.getParentCategory(), "placeholder")) {    //jeśli kategoria nie została wybrana
//            categoryService.update(categoryModel);  //aktualizuj kategorię
//        }else {  //jeśli kategoria != placeholder
//            parentCategoryDTO = categoryService.findById(categoryModel.getParentCategory());    //
//            if(parentCategoryDTO != null)
//                categoryService.update(categoryModel);
//            else
//                throw new IncorrectParentException();
//        }

        return "redirect:/admin/kategoria/pobierz-wszystkie";
    }

    @GetMapping("/edytuj/{kategoriaId}")
    public String editCategory(@PathVariable("kategoriaId") Integer categoryId,
                               Model model){

        model.addAttribute("categoryDTO", categoryService.findById(categoryId));
        model.addAttribute("categoryModel", new CategoryModel());
        model.addAttribute("categories", categoryService.findAllExcept(categoryId));

        return "/views/admin/category/category-edit";
    }

    @PostMapping("/edytuj/przetworz")
    public String editCategoryProcess(Model model,
                                      @Valid @ModelAttribute("categoryModel") CategoryModel categoryModel,
                                      BindingResult bindingResult) throws IncorrectParentException {
        if(bindingResult.hasErrors()){
            model.addAttribute("categoryModel", categoryModel);
            model.addAttribute("categoryDTO", new CategoryModel());
            model.addAttribute("categories",  categoryService.findAllExcept(categoryModel.getId()));

            return "/views/admin/category/category-edit";
        }

        categoryService.update(categoryModel);

//        CategoryDTO parentCategoryDTO;
//        if (Objects.equals(categoryModel.getParentCategory(), "placeholder")){
//            categoryService.update(categoryModel);
//        }else {
//            parentCategoryDTO = categoryService.findById(categoryModel.getParentCategory());
//            if(parentCategoryDTO != null)
//                categoryService.update(categoryModel);
//            else throw new IncorrectParentException();
//        }

        return "redirect:/admin/kategoria/pobierz-wszystkie";
    }

    @GetMapping("/usun/{kategoriaId}")
    public String deleteCategory(@PathVariable("kategoriaId") Integer categoryId) throws NoSuchCategoryException, CanNotDeleteCategotyWithOffers, OfferNotFoundException, OfferStatusNotFoundException {
        categoryService.delete(categoryId);

        return "redirect:/admin/kategoria/pobierz-wszystkie";
    }
}

