package com.cydeo.repository;

import com.cydeo.entity.Category;
import com.cydeo.entity.Company;
import com.cydeo.entity.Product;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * author:AbduShukur
 * date:7/23/2024
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findAllByCompanyIdOrderByDescriptionAsc(@Param("companyId") Long companyId);

    List<Category> findAllByCompany_Title(String title);

//    List<Product> findAllByCategoryOrderByDescriptionAsc(Category category);
}
