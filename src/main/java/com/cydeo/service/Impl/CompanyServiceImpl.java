package com.cydeo.service.Impl;

import com.cydeo.dto.CompanyDto;
import com.cydeo.entity.Company;
import com.cydeo.repository.CompanyRepository;
import com.cydeo.service.CompanyService;
import com.cydeo.util.MapperUtil;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final MapperUtil mapperUtil;

    public CompanyServiceImpl(CompanyRepository companyRepository, MapperUtil mapperUtil) {
        this.companyRepository = companyRepository;
        this.mapperUtil = mapperUtil;
    }

    @Override
    public List<CompanyDto> listAllCompanies() {
         List<Company> companyList= companyRepository.findAll();
        return companyList.stream().map(p->mapperUtil.convert(p, CompanyDto.class)).toList();
    }

    @Override
    public CompanyDto findById(Long id) {
        return mapperUtil.convert(companyRepository.findById(id), CompanyDto.class);
    }
}
