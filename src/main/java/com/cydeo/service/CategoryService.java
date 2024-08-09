package com.cydeo.service;
import com.cydeo.dto.CategoryDto;
import com.cydeo.exception.CategoryNotFoundException;


import java.util.List;

/**
 * author:AbduShukur
 * date:7/23/2024
 */
public interface CategoryService { //fixed
    List<CategoryDto> listAllCategory();
    List<CategoryDto> listAllByCompany();
    CategoryDto findById(Long id);

    CategoryDto update(CategoryDto categoryDto);

    void deleteById(Long id);
   // List<CategoryDto> getCategoriesForCurrentUser(); do we need this method?
    void save(CategoryDto categoryDto);
    CategoryDto findByDescription(String description);




}
