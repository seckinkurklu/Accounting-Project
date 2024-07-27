package com.cydeo.service.impl;


import com.cydeo.dto.CompanyDto;
import com.cydeo.entity.Company;


import com.cydeo.repository.CompanyRepository;
import com.cydeo.service.CompanyService;
import com.cydeo.util.MapperUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service

public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepository companyRepository;
    private final MapperUtil mapperUtil;

    public CompanyServiceImpl(CompanyRepository companyRepository, MapperUtil mapperUtil) {
        this.companyRepository = companyRepository;
        this.mapperUtil = mapperUtil;
    }


    @Override
    public List<CompanyDto> listAllCompanies() {
        List<Company> companies = companyRepository.findAllCompanyIdNot1();

        return companies.stream()
                .map(company -> mapperUtil.convert(company, new CompanyDto()))
                .collect(Collectors.toList());
    }

    @Override
    public CompanyDto findById(Long id) {
        return mapperUtil.convert(companyRepository.findById(id), new CompanyDto());

    }
}
