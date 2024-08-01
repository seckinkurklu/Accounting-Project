package com.cydeo.service.s_impl;

import com.cydeo.dto.CompanyDto;
import com.cydeo.entity.Company;
import com.cydeo.enums.CompanyStatus;
import com.cydeo.exception.CompanyNotFoundException;
import com.cydeo.repository.CompanyRepository;
import com.cydeo.service.CompanyService;
import com.cydeo.service.SecurityService;
import com.cydeo.util.MapperUtil;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final SecurityService securityService;
    private final MapperUtil mapperUtil;

    public CompanyServiceImpl(CompanyRepository companyRepository, SecurityService securityService, MapperUtil mapperUtil) {
        this.companyRepository = companyRepository;
        this.securityService = securityService;
        this.mapperUtil = mapperUtil;
    }


    @Override
    public List<CompanyDto> listAllCompanies() {
        List<Company> companyList= companyRepository.findAll();
        return companyList.stream().map(p->mapperUtil.convert(p, new CompanyDto())).collect(Collectors.toList());
    }

    @Override
    public CompanyDto findById(Long id) {
        return mapperUtil.convert(companyRepository.findById(id), new CompanyDto());
    }

    @Override
    public void save(CompanyDto companyDto) {
        companyDto.setCompanyStatus(CompanyStatus.PASSIVE);
        Company company = mapperUtil.convert(companyDto, new Company());
        companyRepository.save(company);
    }

    @Override
    public void update(CompanyDto companyDto) {
        Optional<Company> foundCompany = companyRepository.findById(companyDto.getId());
        Company convertedCompany = mapperUtil.convert(companyDto, new Company());
        if (foundCompany.isPresent()) {
            convertedCompany.setId(foundCompany.get().getId());
            convertedCompany.setCompanyStatus(CompanyStatus.ACTIVE);
            companyRepository.save(convertedCompany);
        }

    }

    @Override
    public Long getCompanyIdByLoggedInUser() {
        return securityService.getLoggedInUser().getCompany().getId();
    }

    @Override
    public void activateCompany(Long companyId) {
        Company companyToActivate=companyRepository.findById(companyId)
                .orElseThrow(()->new CompanyNotFoundException("Company with id: " + companyId + " Not Found "));
        companyToActivate.setCompanyStatus(CompanyStatus.ACTIVE);
        companyRepository.save(companyToActivate);
    }

    @Override
    public void deactivateCompany(Long companyId) {
        Company companyToDeactivate=companyRepository.findById(companyId)
                .orElseThrow(()->new CompanyNotFoundException("Company with id: " + companyId + " Not Found "));
        companyToDeactivate.setCompanyStatus(CompanyStatus.ACTIVE);
        companyRepository.save(companyToDeactivate);
    }
}