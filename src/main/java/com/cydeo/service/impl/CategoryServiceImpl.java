package com.cydeo.service.impl;

import com.cydeo.dto.CategoryDto;
import com.cydeo.dto.CompanyDto;
import com.cydeo.dto.UserDto;
import com.cydeo.entity.Category;
import com.cydeo.entity.Company;
import com.cydeo.entity.User;
import com.cydeo.repository.CategoryRepository;
import com.cydeo.service.CategoryService;
import com.cydeo.service.UserService;
import com.cydeo.util.MapperUtil;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

/**
 * author:AbduShukur
 * date:7/23/2024
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final MapperUtil mapperUtil;
    private final UserService userService;

    public CategoryServiceImpl(CategoryRepository categoryRepository, MapperUtil mapperUtil, UserService userService) {
        this.categoryRepository = categoryRepository;
        this.mapperUtil = mapperUtil;
        this.userService = userService;
    }

    @Override
    public List<CategoryDto> listAllCategory() {
        List<Category> categoryList = categoryRepository.findAll();
        return categoryList.stream().map(category -> mapperUtil.convert(category,new CategoryDto())).collect(Collectors.toList());
    }

    @Override
    public List<CategoryDto> listAllByCompany() {
        UserDto loggedUser = userService.getLoggedUser();
        CompanyDto companyDto = loggedUser.getCompany();

        List<Category> categoryList = categoryRepository.findAllByCompanyOrderByDescriptionAsc(mapperUtil.convert(companyDto, new Company()));


        return categoryList.stream().map(category -> mapperUtil.convert(category, new CategoryDto())).collect(Collectors.toList());
    }

}
