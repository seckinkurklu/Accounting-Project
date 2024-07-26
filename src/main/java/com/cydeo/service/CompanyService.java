package com.cydeo.service;

import com.cydeo.dto.CompanyDto;

import java.util.List;

public interface CompanyService {
    List<CompanyDto> listAllCompanies();
    CompanyDto findById(Long id);

    void save(CompanyDto company);
}
