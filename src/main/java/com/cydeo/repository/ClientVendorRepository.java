package com.cydeo.repository;

import com.cydeo.entity.ClientVendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ClientVendorRepository extends JpaRepository<ClientVendor, Long> {

    List<ClientVendor> findAllByCompanyTitleAndIsDeletedOrderByClientVendorName(String companyTitle,boolean isDeleted);
    ClientVendor findByClientVendorName(String clientVendorName);

    List<ClientVendor> findAllByCompanyTitleOrderByClientVendorName(String title);
}
