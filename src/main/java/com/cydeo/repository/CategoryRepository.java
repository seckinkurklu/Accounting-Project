package com.cydeo.repository;

import com.cydeo.entity.Category;
import com.cydeo.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * author:AbduShukur
 * date:7/23/2024
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findAllByCompanyOrderByDescriptionAsc(Company company);

    List<Category> findAllByCompany_Title(String title);
}
