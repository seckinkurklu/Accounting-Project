package com.cydeo.repository;

import com.cydeo.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * author:AbduShukur
 * date:7/23/2024
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
