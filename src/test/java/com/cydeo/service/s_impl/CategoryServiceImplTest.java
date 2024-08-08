package com.cydeo.service.s_impl;

import com.cydeo.dto.CategoryDto;
import com.cydeo.dto.CompanyDto;
import com.cydeo.dto.InvoiceProductDto;
import com.cydeo.dto.UserDto;
import com.cydeo.entity.Category;
import com.cydeo.entity.Company;
import com.cydeo.entity.InvoiceProduct;
import com.cydeo.repository.CategoryRepository;
import com.cydeo.repository.ProductRepository;
import com.cydeo.service.CompanyService;
import com.cydeo.service.SecurityService;
import com.cydeo.service.UserService;
import com.cydeo.util.MapperUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private MapperUtil mapperUtil;

    @Mock
    private UserService userService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CompanyService companyService;

    @Mock
    private SecurityService securityService;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    private UserDto mockUserDto;
    private CompanyDto mockCompanyDto;
    private Company mockCompany;
    private Category mockCategory;
    private CategoryDto mockCategoryDto;

    @BeforeEach
    void setUp() {
        mockCompanyDto = new CompanyDto();
        mockCompanyDto.setId(1L);
        mockCompanyDto.setTitle("Test Company");

        mockUserDto = new UserDto();
        mockUserDto.setCompany(mockCompanyDto);

        mockCompany = new Company();
        mockCompany.setId(1L);
        mockCompany.setTitle("Test Company");

        mockCategory = new Category();
        mockCategory.setId(1L);
        mockCategory.setDescription("Test Category");
        mockCategory.setCompany(mockCompany);

        mockCategoryDto = new CategoryDto();
        mockCategoryDto.setId(1L);
        mockCategoryDto.setDescription("Test Category");
        mockCategoryDto.setCompany(mockCompanyDto);
    }

    @Test
    void testListAllCategory() {
        when(userService.getCurrentUser()).thenReturn(mockUserDto);
        when(categoryRepository.findDistinctCategoriesByCompanyAndIsDeletedFalse(1L))
                .thenReturn(Collections.singletonList(mockCategory));
        when(mapperUtil.convert(any(Category.class), any(CategoryDto.class))).thenReturn(mockCategoryDto);

        List<CategoryDto> categoryDtos = categoryService.listAllCategory();

        assertNotNull(categoryDtos);
        assertEquals(1, categoryDtos.size());
        assertEquals("Test Category", categoryDtos.get(0).getDescription());

        verify(userService).getCurrentUser();
        verify(categoryRepository).findDistinctCategoriesByCompanyAndIsDeletedFalse(1L);
        verify(mapperUtil).convert(any(Category.class), any(CategoryDto.class));
    }

    @Test
    void testListAllByCompany() {
        // Arrange
        UserDto mockUserDto = new UserDto();
        CompanyDto mockCompanyDto = new CompanyDto();
        mockCompanyDto.setTitle("Test Company");
        mockUserDto.setCompany(mockCompanyDto);

        Category mockCategory1 = new Category();
        mockCategory1.setId(1L);
        Category mockCategory2 = new Category();
        mockCategory2.setId(1L);

        List<Category> mockCategories = Arrays.asList(mockCategory1, mockCategory2);

        CategoryDto mockCategoryDto1 = new CategoryDto();
        CategoryDto mockCategoryDto2 = new CategoryDto();


        when(userService.getLoggedUser()).thenReturn(mockUserDto);
        when(categoryRepository.findAllByCompany_Title("Test Company")).thenReturn(mockCategories);
        when(mapperUtil.convert(any(Category.class), any(CategoryDto.class))).thenReturn(mockCategoryDto1);
        when(mapperUtil.convert(any(Category.class), any(CategoryDto.class))).thenReturn(mockCategoryDto2);
        when(productRepository.existsByCategory(mockCategory1)).thenReturn(false);
        when(productRepository.existsByCategory(mockCategory2)).thenReturn(false);

        // Act
        List<CategoryDto> result = categoryService.listAllByCompany();

        // Assert
        assertEquals(2, result.size());
        assertThat(result.get(0)).usingRecursiveComparison().isEqualTo(mockCategoryDto1);
        assertThat(result.get(1)).usingRecursiveComparison().isEqualTo(mockCategoryDto2);

    }
    @Test
    void testFindById() {
        when(categoryRepository.findById(anyLong())).thenReturn(java.util.Optional.of(new Category()));
        when(mapperUtil.convert(any(), any())).thenReturn(new CategoryDto());
        CategoryDto result = categoryService.findById(1L);
        assertNotNull(result);
    }

    @Test
    void testSave() {
        when(securityService.getLoggedInUser()).thenReturn(mockUserDto);
        when(mapperUtil.convert(any(CategoryDto.class), any(Category.class))).thenReturn(mockCategory);

        categoryService.save(mockCategoryDto);

        verify(securityService).getLoggedInUser();
        verify(categoryRepository).save(mockCategory);
    }

    @Test
    void testFindByDescription() {
        when(categoryRepository.findByDescription("Test Category")).thenReturn(mockCategory);
        when(mapperUtil.convert(any(Category.class), any(CategoryDto.class))).thenReturn(mockCategoryDto);

        CategoryDto categoryDto = categoryService.findByDescription("Test Category");

        assertNotNull(categoryDto);
        assertEquals("Test Category", categoryDto.getDescription());

        verify(categoryRepository).findByDescription("Test Category");
        verify(mapperUtil).convert(any(Category.class), any(CategoryDto.class));
    }

    @Test
    void testExistCategory() {
        when(categoryRepository.existsById(1L)).thenReturn(true);

        boolean exists = categoryService.existCategory(1L);

        assertTrue(exists);
        verify(categoryRepository).existsById(1L);
    }

    @Test
    void testUpdate() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(mockCategory));
        when(mapperUtil.convert(any(Category.class), any(CategoryDto.class))).thenReturn(mockCategoryDto);

        CategoryDto updatedCategory = categoryService.update(mockCategoryDto);

        assertNotNull(updatedCategory);
        assertEquals("Test Category", updatedCategory.getDescription());

        verify(categoryRepository).findById(1L);
        verify(categoryRepository).save(mockCategory);
        verify(mapperUtil).convert(any(Category.class), any(CategoryDto.class));
    }

    @Test
    void testDeleteById() {
        categoryService.deleteById(1L);

        verify(categoryRepository).deleteById(1L);
    }
}
