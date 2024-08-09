package com.cydeo.repository;

import com.cydeo.entity.Category;
import com.cydeo.entity.Company;
import com.cydeo.entity.Product;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * author:AbduShukur
 * date:7/23/2024
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findAllByCompanyIdOrderByDescriptionAsc(@Param("companyId") Long companyId);

    List<Category> findAllByCompany_Title(String title);
    Optional<Category> findByDescriptionAndCompany_Title(String description, String title);
    
    Category findByDescription(String description);


    @Query("SELECT DISTINCT c FROM Category c WHERE c.company.id = :companyId AND c.isDeleted = false ORDER BY c.description ASC")
    List<Category> findDistinctCategoriesByCompanyAndIsDeletedFalse(@Param("companyId") Long companyId);



//    List<Product> findAllByCategoryOrderByDescriptionAsc(Category category);

}
