package com.cydeo.repository;

import com.cydeo.entity.ClientVendor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClientVendorRepository extends JpaRepository<ClientVendor, Long> {

    List<ClientVendor> findAllByCompanyTitleAndIsDeletedOrderByClientVendorName(String companyTitle,boolean isDeleted);
    ClientVendor findByClientVendorName(String clientVendorName);

}
