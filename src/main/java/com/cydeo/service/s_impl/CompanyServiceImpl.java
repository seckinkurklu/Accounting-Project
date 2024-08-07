package com.cydeo.service.s_impl;

import com.cydeo.client.CountryClient;
import com.cydeo.dto.CompanyDto;
import com.cydeo.dto.CountryInfoDto;
import com.cydeo.dto.RoleDto;
import com.cydeo.entity.Company;
import com.cydeo.enums.CompanyStatus;
import com.cydeo.exception.CompanyNotFoundException;
import com.cydeo.repository.CompanyRepository;
import com.cydeo.service.CompanyService;
import com.cydeo.service.SecurityService;
import com.cydeo.service.UserService;
import com.cydeo.util.MapperUtil;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final SecurityService securityService;
    private final MapperUtil mapperUtil;
    private final UserService userService;
    private final CountryClient countryClient;

    public CompanyServiceImpl(CompanyRepository companyRepository, SecurityService securityService, MapperUtil mapperUtil, UserService userService, CountryClient countryClient) {
        this.companyRepository = companyRepository;
        this.securityService = securityService;
        this.mapperUtil = mapperUtil;
        this.userService = userService;
        this.countryClient = countryClient;
    }


    @Override
    public List<CompanyDto> listAllCompanies() {
        List<Company> companyList = companyRepository.findAll();
        return companyList.stream().map(p -> mapperUtil.convert(p, new CompanyDto())).collect(Collectors.toList());
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
        Company companyToActivate = companyRepository.findById(companyId)
                .orElseThrow(() -> new CompanyNotFoundException("Company with id: " + companyId + " Not Found "));
        companyToActivate.setCompanyStatus(CompanyStatus.ACTIVE);
        companyRepository.save(companyToActivate);
    }

    @Override
    public void deactivateCompany(Long companyId) {
        Company companyToDeactivate = companyRepository.findById(companyId)
                .orElseThrow(() -> new CompanyNotFoundException("Company with id: " + companyId + " Not Found "));
        companyToDeactivate.setCompanyStatus(CompanyStatus.PASSIVE);
        companyRepository.save(companyToDeactivate);


    }

    @Override
    public List<CompanyDto> listCompaniesByLoggedInUser() {
        RoleDto loggedInUserRole = userService.getLoggedUser().getRole();
        List<Company> companyList = new ArrayList<>();

        if (loggedInUserRole.getDescription().equals("Root User")) {
            companyList.addAll(companyRepository.findCompaniesByTitleIsNot("CYDEO"));
        } else {
            companyList.add(companyRepository.findById(userService.getLoggedUser().getCompany().getId()).get());
        }

        return companyList.stream().map(company -> mapperUtil.convert(company, new CompanyDto())).collect(Collectors.toList());
    }

    @Override
    public List<String> getCountries() {
        List<CountryInfoDto> counryList= countryClient.getCountries().getBody();

        return  counryList.stream()
                .map(CountryInfoDto::getName).toList();
    }
}