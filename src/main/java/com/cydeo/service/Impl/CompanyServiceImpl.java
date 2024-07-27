package com.cydeo.service.Impl;

import com.cydeo.dto.AddressDto;
import com.cydeo.dto.CompanyDto;
import com.cydeo.entity.Company;
import com.cydeo.enums.CompanyStatus;
import com.cydeo.repository.CompanyRepository;
import com.cydeo.service.CompanyService;
import com.cydeo.util.MapperUtil;
import org.springframework.data.domain.Sort;
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
         List<Company> companyList= companyRepository.findAll(Sort.by(Sort.Direction.ASC, "title")); // Order by title Asc
        return companyList.stream().map(p->mapperUtil.convert(p, new CompanyDto())).toList();
    }

    @Override
    public CompanyDto findById(Long id) {
        return mapperUtil.convert(companyRepository.findById(id), new CompanyDto());
    }

    @Override
    public void save(CompanyDto company) {
        AddressDto addressDto = new AddressDto();
        addressDto.setAddressLine1(company.getAddress().getAddressLine1());
        addressDto.setAddressLine2(company.getAddress().getAddressLine2());
        addressDto.setCity(company.getAddress().getCity());
        addressDto.setCountry(company.getAddress().getCountry());
        addressDto.setZipCode(company.getAddress().getZipCode());
        addressDto.setState(company.getAddress().getState());

        company.setAddress(addressDto);
        company.setTitle(company.getTitle());
        company.setPhone(company.getPhone());
        company.setWebsite(company.getWebsite());
        company.setCompanyStatus(CompanyStatus.PASSIVE);
        Company converted = mapperUtil.convert(company, new Company());
        companyRepository.save(converted);
    }
}
