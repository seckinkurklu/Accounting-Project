package com.cydeo.service.s_impl;

import com.cydeo.dto.UserDto;
import com.cydeo.repository.CategoryRepository;
import com.cydeo.service.UserService;
import com.cydeo.util.MapperUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    @Mock
    CategoryRepository categoryRepository;
    @Mock
    MapperUtil mapperUtil;

    @Mock
    UserService userService;

    @InjectMocks
    CategoryServiceImpl categoryService;

    @Test
    void listAllCategory() {
        UserDto userDto = new UserDto();
        userDto.setId(1L);
        categoryService.listAllCategory();
        verify(categoryRepository).findDistinctCategoriesByCompanyAndIsDeletedFalse(1L);

    }

    @Test
    void listAllByCompany() {
    }

    @Test
    void findCategoryById() {
    }

    @Test
    void save() {
    }

    @Test
    void findByDescription() {
    }

    @Test
    void existCategory() {
    }

    @Test
    void update() {
    }

    @Test
    void getCategoryById() {
    }

    @Test
    void deleteById() {
    }

    @Test
    void getCategoriesForCurrentUser() {
    }
}

