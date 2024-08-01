package com.cydeo.service;

import com.cydeo.dto.CompanyDto;

import java.util.List;

public interface CompanyService {
    List<CompanyDto> listAllCompanies();
    CompanyDto findById(Long id);
    Long getCompanyIdByLoggedInUser();
    void save(CompanyDto companyDto);
    void update(CompanyDto companyDto);
    void activateCompany(Long companyId);
    void deactivateCompany(Long companyId);
    List<CompanyDto> listCompaniesByLoggedInUser();
}
