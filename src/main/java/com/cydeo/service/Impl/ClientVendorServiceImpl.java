package com.cydeo.service.impl;

import com.cydeo.dto.ClientVendorDto;
import com.cydeo.entity.ClientVendor;
import com.cydeo.entity.User;
import com.cydeo.repository.ClientVendorRepository;
import com.cydeo.repository.UserRepository;
import com.cydeo.service.ClientVendorService;
import com.cydeo.util.MapperUtil;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientVendorServiceImpl implements ClientVendorService {

    private final ClientVendorRepository clientVendorRepository;
    private final UserRepository userRepository;
    private final MapperUtil mapperUtil;

    public ClientVendorServiceImpl(ClientVendorRepository clientVendorRepository, UserRepository userRepository, MapperUtil mapperUtil) {
        this.clientVendorRepository = clientVendorRepository;
        this.userRepository = userRepository;
        this.mapperUtil = mapperUtil;
    }

    @Override
    public List<ClientVendorDto> listAllClientVendor() {
        List<ClientVendor> clientVendorRepositoryAll = clientVendorRepository.findAll();
        return clientVendorRepositoryAll.stream().map(p->mapperUtil.convert(p, new ClientVendorDto())).toList();
    }

    @Override
    public List<ClientVendorDto> listAllByCompanyTitle() {
        String username= SecurityContextHolder.getContext().getAuthentication().getName();
        User byUsername = userRepository.findByUsername(username);
        List<ClientVendor> allByCompanyTitle = clientVendorRepository.findAllByCompanyTitleOrderByClientVendorName(byUsername.getCompany().getTitle());
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

    }

    @Override
    public void update(ClientVendorDto clientVendorDto) {

    }
}
