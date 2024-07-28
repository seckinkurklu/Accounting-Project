package com.cydeo.service.impl;

import com.cydeo.dto.ClientVendorDto;
import com.cydeo.dto.UserDto;
import com.cydeo.entity.ClientVendor;
import com.cydeo.entity.User;
import com.cydeo.repository.ClientVendorRepository;
import com.cydeo.repository.UserRepository;
import com.cydeo.service.ClientVendorService;
import com.cydeo.service.SecurityService;
import com.cydeo.util.MapperUtil;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClientVendorServiceImpl implements ClientVendorService {

    private final ClientVendorRepository clientVendorRepository;
    private final UserRepository userRepository;
    private final MapperUtil mapperUtil;
    private final SecurityService securityService;

    public ClientVendorServiceImpl(ClientVendorRepository clientVendorRepository, UserRepository userRepository, MapperUtil mapperUtil, SecurityService securityService) {
        this.clientVendorRepository = clientVendorRepository;
        this.userRepository = userRepository;
        this.mapperUtil = mapperUtil;
        this.securityService = securityService;
    }

    @Override
    public List<ClientVendorDto> listAllClientVendor() {
        List<ClientVendor> clientVendorRepositoryAll = clientVendorRepository.findAll();
        return clientVendorRepositoryAll.stream().map(p->mapperUtil.convert(p, new ClientVendorDto())).toList();
    }

    // US-33 Only client and vendors of the current user's company should be listed in the list.
    //List should be sorted based on type (clients at the top) then Client / Vendor Name.
        @Override
    public List<ClientVendorDto> listAllByCompanyTitle() {
        UserDto loggedInUser = securityService.getLoggedInUser();
        List<ClientVendor> allByCompanyTitle = clientVendorRepository.findAllByCompanyTitleOrderByClientVendorName(loggedInUser.getCompany().getTitle());
        return allByCompanyTitle.stream().map(p->mapperUtil.convert(p, new ClientVendorDto())).toList();
    }

    @Override
    public ClientVendorDto findById(Long id) {
        Optional<ClientVendor> byId = clientVendorRepository.findById(id);
        return mapperUtil.convert(byId, new ClientVendorDto());
    }

    @Override
    public ClientVendorDto findByClientVendorName(String username) {
        ClientVendor byClientVendorName = clientVendorRepository.findByClientVendorName(username);

        return mapperUtil.convert(byClientVendorName, new ClientVendorDto());
    }

    @Override
    public void save(ClientVendorDto clientVendorDto) {

    }



    @Override
    public void update(ClientVendorDto clientVendorDto) {
        if (clientVendorDto==null || clientVendorDto.getId()==null){
            throw new IllegalArgumentException("ClientVendorDto or ClientVendor ID cannot be null");

        }


        ClientVendor clientVendor = clientVendorRepository.getReferenceById(clientVendorDto.getId());
        if (clientVendor==null){
            throw new EntityNotFoundException("Client/Vendor cannot be found with ID "+clientVendor.getId());

        }

        ClientVendor convertedClientVendor=mapperUtil.convert(clientVendorDto,new ClientVendor());
        convertedClientVendor.setId(clientVendor.getId());
        convertedClientVendor.setAddress(clientVendor.getAddress());
        clientVendorRepository.save(convertedClientVendor);

    }

}
