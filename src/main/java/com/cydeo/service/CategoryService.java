package com.cydeo.service;

import com.cydeo.dto.CategoryDto;
import com.cydeo.dto.ClientVendorDto;

import java.util.List;

/**
 * author:AbduShukur
 * date:7/23/2024
 */
public interface CategoryService {
    List<CategoryDto> listAllCategory();

    CategoryDto findById(Long id);
}
