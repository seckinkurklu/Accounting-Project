package com.cydeo.controller;


import com.cydeo.dto.CategoryDto;
import com.cydeo.service.CategoryService;
import com.cydeo.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * author:AbduShukur
 * date:7/24/2024
 */
@Controller
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;
    private final ProductService productService;

    public CategoryController(CategoryService categoryService, ProductService productService) {
        this.categoryService = categoryService;
        this.productService = productService;
    }


    @GetMapping("/list")
    public String CategoryList(Model model) {
        model.addAttribute("categories",categoryService.listAllByCompany());
        return "/category/category-list";
    }


    @GetMapping("/create")
    public String creatCategory(Model model){
        model.addAttribute("newCategory",new CategoryDto());
        return "/category/category-create";
    }


    @PostMapping("/create")
    public String create(@ModelAttribute("newCategory") @Valid CategoryDto categoryDto, BindingResult result) {
        if(result.hasErrors()) {
            return "/category/category-create";
        }
        categoryService.save(categoryDto);
        return "redirect:/categories/list";
    }


    @GetMapping("/update/{id}")
    public String updateCategoryForm1(@PathVariable("id") Long id, Model model) {
        model.addAttribute("category", categoryService.getCategoryById(id));
        model.addAttribute("product", productService.listAllProducts());
        return "category/category-update";
    }


    @PostMapping("/update/{id}")
    public String updateCategoryForm2( @ModelAttribute("category") @Valid CategoryDto categoryDto,BindingResult result) {
        if (result.hasErrors()) {
            return "category/category-update";
        }
        categoryService.update(categoryDto);
        return "redirect:/categories/list";
    }
    
}
