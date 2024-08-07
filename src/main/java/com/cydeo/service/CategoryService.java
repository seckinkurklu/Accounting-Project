package com.cydeo.service;
import com.cydeo.dto.CategoryDto;


import java.util.List;

/**
 * author:AbduShukur
 * date:7/23/2024
 */
public interface CategoryService {
    List<CategoryDto> listAllCategory();
    List<CategoryDto> listAllByCompany();
    CategoryDto findCategoryById(Long id);

    CategoryDto update(CategoryDto categoryDto);

    CategoryDto getCategoryById(Long id);

    void deleteById(Long id);
    List<CategoryDto> getCategoriesForCurrentUser();
    void save(CategoryDto categoryDto);
    CategoryDto findByDescription(String description);




}
