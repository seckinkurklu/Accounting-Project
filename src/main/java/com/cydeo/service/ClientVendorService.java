package com.cydeo.service;

import com.cydeo.dto.ClientVendorDto;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ClientVendorService {

    List<ClientVendorDto> listAllClientVendor();

    ClientVendorDto findById(Long id);


}
