package com.cydeo.service.s_impl;

import com.cydeo.dto.CategoryDto;
import com.cydeo.dto.CompanyDto;
import com.cydeo.dto.UserDto;
import com.cydeo.entity.Category;
import com.cydeo.entity.Company;
import com.cydeo.repository.CategoryRepository;
import com.cydeo.repository.ProductRepository;
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
    private final ProductRepository productRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository, MapperUtil mapperUtil, UserService userService, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.mapperUtil = mapperUtil;
        this.userService = userService;
        this.productRepository = productRepository;
    }

    @Override
    public List<CategoryDto> listAllCategory() {
        List<Category> categoryList = categoryRepository.findAll();
        return categoryList.stream().map(category -> mapperUtil.convert(category,new CategoryDto())).collect(Collectors.toList());
    }

    @Override
    public List<CategoryDto> listAllByCompany() {
        UserDto loggedUser = userService.getLoggedUser();
        String title = loggedUser.getCompany().getTitle();
        List<Category> categories = categoryRepository.findAllByCompany_Title(title);
        return categories.stream().map(category -> {
            CategoryDto categoryDto = mapperUtil.convert(category,new CategoryDto());
            boolean hasProduct = productRepository.existsByCategory(category);
            categoryDto.setHasProduct(hasProduct);
            return categoryDto;
        }).toList();
    }

    @Override
    public CategoryDto findCategoryById(Long id) {

        return mapperUtil.convert(categoryRepository.findById(id).get(),new CategoryDto());
    }

}
