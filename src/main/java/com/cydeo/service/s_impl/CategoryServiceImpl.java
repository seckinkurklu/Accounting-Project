package com.cydeo.service.s_impl;

import com.cydeo.dto.CategoryDto;
import com.cydeo.dto.CompanyDto;
import com.cydeo.dto.UserDto;
import com.cydeo.entity.Category;

import com.cydeo.service.CompanyService;

import com.cydeo.entity.Company;
import com.cydeo.entity.User;
import com.cydeo.repository.CategoryRepository;
import com.cydeo.repository.ProductRepository;
import com.cydeo.service.CategoryService;
import com.cydeo.service.SecurityService;

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

    private final CompanyService companyService;

    private final SecurityService securityService;

    public CategoryServiceImpl(CategoryRepository categoryRepository, MapperUtil mapperUtil, UserService userService, ProductRepository productRepository, CompanyService companyService, SecurityService securityService) {
        this.categoryRepository = categoryRepository;
        this.mapperUtil = mapperUtil;
        this.userService = userService;
        this.productRepository = productRepository;
        this.companyService = companyService;
        this.securityService = securityService;
    }


    @Override
    public List<CategoryDto> listAllCategory() {
        UserDto currentUser = userService.getCurrentUser();
        Long companyId = currentUser.getCompany().getId();

        return categoryRepository.findDistinctCategoriesByCompanyAndIsDeletedFalse(companyId)
                .stream()
                .map(category -> mapperUtil.convert(category, new CategoryDto()))
                .collect(Collectors.toList());
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

        return mapperUtil.convert(categoryRepository.findById(id),new CategoryDto());
    }

    @Override
    public void save(CategoryDto categoryDto) {
        UserDto loggedInUser = securityService.getLoggedInUser();
        CompanyDto loggedInUserCompany = loggedInUser.getCompany();
        categoryDto.setCompany(loggedInUserCompany);
        categoryRepository.save(mapperUtil.convert(categoryDto,new Category()));
    }

    @Override
    public CategoryDto findByDescription(String description) {
        Category byDescription = categoryRepository.findByDescription(description);
        return mapperUtil.convert(byDescription, new CategoryDto());
    }


    public boolean existCategory(Long id) {
        return categoryRepository.existsById(id);
    }

    @Override
    public CategoryDto update(CategoryDto categoryDto) {
        Category category = categoryRepository.findById(categoryDto.getId()).get();
        category.setDescription(categoryDto.getDescription());
        categoryRepository.save(category);
        return mapperUtil.convert(category, categoryDto);
    }

    @Override
    public CategoryDto getCategoryById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow();
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setDescription(category.getDescription());
        categoryDto.setCompany(companyService.findById(category.getCompany().getId()));
        return categoryDto;
    }

    @Override
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public List<CategoryDto> getCategoriesForCurrentUser() {
        UserDto currentUser = userService.getCurrentUser();
        Company company = mapperUtil.convert(currentUser,new User()).getCompany();

        List<Category> categories = categoryRepository.findAllByCompanyIdOrderByDescriptionAsc(company.getId());

        return categories.stream()
                .map(category -> mapperUtil.convert(category,new CategoryDto())).collect(Collectors.toList());



    }
//
//    @Override
//    public List<CategoryDto> listAllCategories() {
//        UserDto currentUser = userService.getCurrentUser();
//        Long companyId = currentUser.getCompany().getId();
//
//        return categoryRepository.findDistinctCategoriesByCompanyAndIsDeletedFalse(companyId)
//                .stream()
//                .map(category -> mapperUtil.convert(category, new CategoryDto()))
//                .collect(Collectors.toList());
//    }



}
