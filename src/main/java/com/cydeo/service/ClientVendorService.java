package com.cydeo.service;

import com.cydeo.dto.ClientVendorDto;

import java.util.List;

public interface ClientVendorService {
    List<ClientVendorDto> listAllClientVendor();
    List<ClientVendorDto> listAllByCompanyTitle();
    ClientVendorDto findById(Long l);
    ClientVendorDto findByClientVendorName(String username);

    void save(ClientVendorDto clientVendorDto);
    void update(ClientVendorDto clientVendorDto);

}
