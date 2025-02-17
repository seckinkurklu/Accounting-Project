package com.cydeo.service.s_impl;

import com.cydeo.dto.ClientVendorDto;
import com.cydeo.entity.ClientVendor;
import com.cydeo.entity.User;
import com.cydeo.exception.UserNotFoundException;

import com.cydeo.exception.ClientVendorNotFoundException;

import com.cydeo.repository.ClientVendorRepository;
import com.cydeo.repository.UserRepository;
import com.cydeo.service.ClientVendorService;
import com.cydeo.service.InvoiceService;
import com.cydeo.service.SecurityService;
import com.cydeo.util.MapperUtil;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientVendorServiceImpl implements ClientVendorService {

    private final ClientVendorRepository clientVendorRepository;
    private final UserRepository userRepository;
    private final MapperUtil mapperUtil;
    private final InvoiceService invoiceService;
    private final SecurityService securityService;

    public ClientVendorServiceImpl(ClientVendorRepository clientVendorRepository, UserRepository userRepository, MapperUtil mapperUtil, @Lazy InvoiceService invoiceService, SecurityService securityService) {
        this.clientVendorRepository = clientVendorRepository;
        this.userRepository = userRepository;
        this.mapperUtil = mapperUtil;
        this.invoiceService = invoiceService;
        this.securityService = securityService;
    }

//    @Override
//    public List<ClientVendorDto> listAllClientVendor() {
//        String username= SecurityContextHolder.getContext().getAuthentication().getName();
//        User byUsername = userRepository.findByUsername(username).get();
//        List<ClientVendor> allByCompanyTitle = clientVendorRepository.findAllByCompanyTitleOrderByClientVendorName(byUsername.getCompany().getTitle());
//        return allByCompanyTitle.stream().map(p->mapperUtil.convert(p, new ClientVendorDto())).toList();
//
//    } it does not necessary

    @Override
    public List<ClientVendorDto> listAllByCompanyTitle() {
        String username= SecurityContextHolder.getContext().getAuthentication().getName();
        User byUsername = userRepository.findByUsername(username)
                .orElseThrow(()-> new UserNotFoundException("User Name: " + username + "Not Found"));
        List<ClientVendor> allByCompanyTitle = clientVendorRepository.findAllByCompanyTitleAndIsDeletedOrderByClientVendorName(byUsername.getCompany()
                .getTitle(),false);
        List<ClientVendorDto> clientVendorList= allByCompanyTitle.stream().map(p->mapperUtil.convert(p, new ClientVendorDto())).collect(Collectors.toList());
        List<ClientVendorDto> filteredList=clientVendorList.stream().map(cv->{
                    if( invoiceService.existByClientVendorId(cv.getId())){
                        cv.setHasInvoice(true);
                    }return cv;
                }
        ).toList();
        return filteredList;
    }

    @Override
    public ClientVendorDto findById(Long id) {
       ClientVendor clientVendor = clientVendorRepository.findById(id)
                .orElseThrow(() -> new ClientVendorNotFoundException("clientVendor can not found with id: " + id));

        return mapperUtil.convert(clientVendor, new ClientVendorDto());
    }

    @Override
    public ClientVendorDto findByClientVendorName(String username) {
        ClientVendor byClientVendorName = clientVendorRepository.findByClientVendorName(username);

        return mapperUtil.convert(byClientVendorName, new ClientVendorDto());
    }


    @Override
    public ClientVendorDto save(ClientVendorDto clientVendorDto) {
        String username= SecurityContextHolder.getContext().getAuthentication().getName(); // find username who logged to system.
        User user= userRepository.findByUsername(username)
                .orElseThrow(()-> new UserNotFoundException("User Name: " + username + "Not Found")); // from DB, we get that user.

        ClientVendor clientVendor = mapperUtil.convert(clientVendorDto,new ClientVendor());
        clientVendor.setCompany(user.getCompany());
        clientVendorRepository.save(clientVendor);

        return clientVendorDto;
    }



    @Override
    public ClientVendorDto update(ClientVendorDto clientVendorDto) {
        if (clientVendorDto==null || clientVendorDto.getId()==null){
            throw new IllegalArgumentException("ClientVendorDto or ClientVendor ID cannot be null");

        }


//        ClientVendor clientVendor = clientVendorRepository.getReferenceById(clientVendorDto.getId());
//        if (clientVendor==null){
//            throw new EntityNotFoundException("Client/Vendor cannot be found with ID "+clientVendor.getId());
//
//        }

        ClientVendor convertedClientVendor = mapperUtil.convert(clientVendorDto,new ClientVendor());
        ClientVendor clientVendor = clientVendorRepository.findById(clientVendorDto.getId())
                .orElseThrow(() -> new EntityNotFoundException("Client/Vendor cannot be found with ID " + clientVendorDto.getId()));

        clientVendor.setId(clientVendorDto.getId());
        clientVendor.setAddress(convertedClientVendor.getAddress());
        clientVendor.setClientVendorName(convertedClientVendor.getClientVendorName());
        clientVendor.setWebsite(convertedClientVendor.getWebsite());
        clientVendor.setAddress(convertedClientVendor.getAddress());

        clientVendorRepository.save(clientVendor);

        return mapperUtil.convert(clientVendor,new ClientVendorDto());
    }


    @Override
    public void delete(Long id) {
        ClientVendor clientVendor=clientVendorRepository.findById(id).orElseThrow(
                    () -> new EntityNotFoundException("Client/Vendor cannot be found with ID "+id)
            );
//        ClientVendorDto convertedClientVendor=mapperUtil.convert(clientVendor,new ClientVendorDto());
//      boolean hasInvoiceByClientVendorId=  invoiceService.existByClientVendorId(convertedClientVendor.getId());
//       if (! hasInvoiceByClientVendorId){
       clientVendor.setIsDeleted(true);
            clientVendorRepository.save(clientVendor);}
//    }

}