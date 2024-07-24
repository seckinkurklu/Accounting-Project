package com.cydeo.repository;

import com.cydeo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    @Query("select u from User u join u.role r where r.description = : roleDescription order by u.company.title asc , r.description asc ")
    List<User> findAllUsersSorted(@Param("roleDescription")String roleDescription);



    @Query("SELECT u FROM User u WHERE u.company = :company ORDER BY u.company.title ASC")
    List<User> findAllCompanySorted(@Param("company") String company);






}
