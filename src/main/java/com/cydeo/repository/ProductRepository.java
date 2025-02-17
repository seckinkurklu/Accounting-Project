package com.cydeo.repository;

import com.cydeo.entity.Category;
import com.cydeo.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional< Product> findByIdAndIsDeleted(Long id, boolean deleted);
    boolean existsByCategory(Category category);
    List<Product> findByCategory(Category category);
    @Query("SELECT p FROM Product p " +
            "JOIN Category ca ON p.category.id = ca.id " +
            "JOIN Company c ON ca.company.id = c.id " +
            "WHERE c.id = :companyId  and  p.isDeleted=:isDeleted order by ca.description , p.name asc")
    List<Product> findAllByCompanyIdAndIsDeleted(Long companyId,boolean isDeleted);
}
