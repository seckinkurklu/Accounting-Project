package com.cydeo.service.impl;
import com.cydeo.dto.ClientVendorDto;
import com.cydeo.entity.ClientVendor;
import com.cydeo.repository.ClientVendorRepository;
import com.cydeo.service.ClientVendorService;
import com.cydeo.util.MapperUtil;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientVendorServiceImpl implements ClientVendorService {

    private final ClientVendorRepository clientVendorRepository;
    private final MapperUtil mapperUtil;

    public ClientVendorServiceImpl(ClientVendorRepository clientVendorRepository, MapperUtil mapperUtil) {
        this.clientVendorRepository = clientVendorRepository;
        this.mapperUtil = mapperUtil;
    }

    @Override
    public List<ClientVendorDto> listAllClientVendor() {
        List<ClientVendor> clientVendors = clientVendorRepository.findAll();
       return clientVendors.stream().map(clientVendor -> mapperUtil.convert(clientVendor,ClientVendorDto.class)).collect(Collectors.toList());

    }

    @Override
    public ClientVendorDto findById(Long id) {
//        ClientVendor clientVendor = clientVendorRepository.findById(id).get();
//        return mapperUtil.convert(clientVendor,ClientVendorDto.class);
        return clientVendorRepository.findById(id)
                .map(clientVendor -> mapperUtil.convert(clientVendor, ClientVendorDto.class))
                .orElse(null);
    }

    @Override
    @Transactional
    public void save(ClientVendorDto clientVendorDto) {
        ClientVendor clientVendor = mapperUtil.convert(clientVendorDto,ClientVendor.class);
        if (clientVendor.getAddress() != null && clientVendor.getAddress().getId() == null) {
            clientVendorRepository.save(clientVendor);
        }

//        clientVendorRepository.save(clientVendor);
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

        ClientVendor convertedClientVendor=mapperUtil.convert(clientVendorDto,ClientVendor.class);
        convertedClientVendor.setId(clientVendor.getId());
        convertedClientVendor.setAddress(clientVendor.getAddress());
        clientVendorRepository.save(convertedClientVendor);

    }
}
