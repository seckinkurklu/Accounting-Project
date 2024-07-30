package com.cydeo.controller;


import com.cydeo.dto.CategoryDto;
import com.cydeo.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * author:AbduShukur
 * date:7/24/2024
 */
@Controller
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/list")
    public String CategoryList(Model model) {
        model.addAttribute("categories",categoryService.listAllByCompany());
        return "/category/category-list";
    }

    @GetMapping("/create")
    public String createCategory(Model model) {
        model.addAttribute("newCategory", new CategoryDto());
        model.addAttribute("categories",categoryService.listAllByCompany());
        return "/category/category-create";
    }

    @PostMapping("/create")
    public String createCategory(@ModelAttribute CategoryDto categoryDto, Model model) {
        categoryService.save(categoryDto);
        return "redirect:/categories/list";
    }


    
}
