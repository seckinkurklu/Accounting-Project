package com.cydeo.service.impl;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.cydeo.dto.ClientVendorDto;
import com.cydeo.entity.ClientVendor;
import com.cydeo.entity.Company;
import com.cydeo.entity.User;
import com.cydeo.exception.ClientVendorNotFoundException;
import com.cydeo.exception.UserNotFoundException;
import com.cydeo.repository.ClientVendorRepository;
import com.cydeo.repository.UserRepository;
import com.cydeo.service.InvoiceService;
import com.cydeo.service.s_impl.ClientVendorServiceImpl;
import com.cydeo.util.MapperUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
public class ClientVendorServiceImplUnitTest {

    @Mock
    private ClientVendorRepository clientVendorRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private MapperUtil mapperUtil;

    @Mock
    private InvoiceService invoiceService;

    @InjectMocks
    private ClientVendorServiceImpl clientVendorService;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;


    private ClientVendor clientVendor;
    private ClientVendorDto clientVendorDto;
    ClientVendor clientvendor1;
    ClientVendor clientVendor2;
    ClientVendorDto clientVendorDto1;
    ClientVendorDto clientVendorDto2;
    private List<ClientVendor> clientVendorList;
    private List<ClientVendorDto> clientVendorDtoList;
    private User user;
    private Company company;



    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        clientVendorList = new ArrayList<>();
        ClientVendor clientVendor1 = new ClientVendor();
        clientVendor1.setClientVendorName("VendorName1");
        ClientVendor clientVendor2 = new ClientVendor();
        clientVendor2.setClientVendorName("VendorName2");

        clientVendorList.add(clientVendor1);
        clientVendorList.add(clientVendor2);

        clientVendorDtoList = new ArrayList<>();
        ClientVendorDto clientVendorDto1 = new ClientVendorDto();
        clientVendorDto1.setClientVendorName("VendorName1");
        ClientVendorDto clientVendorDto2 = new ClientVendorDto();
        clientVendorDto2.setClientVendorName("VendorName2");

        clientVendorDtoList.add(clientVendorDto1);
        clientVendorDtoList.add(clientVendorDto2);


        clientVendor = new ClientVendor();
        clientVendor.setClientVendorName("VendorName");

        clientVendorDto = new ClientVendorDto();
        clientVendorDto.setClientVendorName("VendorName");



        clientVendor = new ClientVendor();
        clientVendor.setId(1L);
        clientVendor.setClientVendorName("VendorName");

        clientVendorDto = new ClientVendorDto();
        clientVendorDto.setId(1L);
        clientVendorDto.setClientVendorName("VendorName");

        company = new Company();
        company.setTitle("CompanyName");

        user = new User();
        user.setUsername("testUser");
        user.setCompany(company);

        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("testUser");

    }

    @Test
    void testUpdate_Success() {
        when(clientVendorRepository.getReferenceById(clientVendorDto.getId())).thenReturn(clientVendor);
        when(mapperUtil.convert(clientVendorDto, new ClientVendor())).thenReturn(clientVendor);

        ClientVendorDto result = clientVendorService.update(clientVendorDto);

        assertEquals(clientVendorDto, result);
        verify(clientVendorRepository, times(1)).getReferenceById(clientVendorDto.getId());
        verify(clientVendorRepository, times(1)).save(clientVendor);
        verify(mapperUtil, times(1)).convert(clientVendorDto, new ClientVendor());
    }

    @Test
    void testUpdate_NullClientVendorDto() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            clientVendorService.update(null);
        });

        String expectedMessage = "ClientVendorDto or ClientVendor ID cannot be null";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(clientVendorRepository, times(0)).getReferenceById(anyLong());
        verify(clientVendorRepository, times(0)).save(any(ClientVendor.class));
        verify(mapperUtil, times(0)).convert(any(ClientVendorDto.class), any(ClientVendor.class));
    }
    @Test
    void testUpdate_NullClientVendorId() {
        clientVendorDto.setId(null);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            clientVendorService.update(clientVendorDto);
        });

        String expectedMessage = "ClientVendorDto or ClientVendor ID cannot be null";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(clientVendorRepository, times(0)).getReferenceById(anyLong());
        verify(clientVendorRepository, times(0)).save(any(ClientVendor.class));
        verify(mapperUtil, times(0)).convert(any(ClientVendorDto.class), any(ClientVendor.class));
    }

    @Test
    void testUpdate_EntityNotFound() {
        when(clientVendorRepository.getReferenceById(clientVendorDto.getId())).thenReturn(null);

        Exception exception = assertThrows(EntityNotFoundException.class, () -> {
            clientVendorService.update(clientVendorDto);
        });

        String expectedMessage = "Client/Vendor cannot be found with ID " + clientVendorDto.getId();
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(clientVendorRepository, times(1)).getReferenceById(clientVendorDto.getId());
        verify(clientVendorRepository, times(0)).save(any(ClientVendor.class));
        verify(mapperUtil, times(0)).convert(any(ClientVendorDto.class), any(ClientVendor.class));
    }


    @Test
    void testSave() {
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));
        when(mapperUtil.convert(clientVendorDto, new ClientVendor())).thenReturn(clientVendor);

        ClientVendorDto result = clientVendorService.save(clientVendorDto);

        assertEquals(clientVendorDto, result);
        assertEquals(company, clientVendor.getCompany());
        verify(clientVendorRepository, times(1)).save(clientVendor);
        verify(userRepository, times(1)).findByUsername("testUser");
        verify(mapperUtil, times(1)).convert(clientVendorDto, new ClientVendor());
    }

    @Test
    void testSave_UserNotFound() {
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.empty());

        Exception exception = assertThrows(UserNotFoundException.class, () -> {
            clientVendorService.save(clientVendorDto);
        });

        String expectedMessage = "User Name: testUser Not Found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(userRepository, times(1)).findByUsername("testUser");
        verify(mapperUtil, times(0)).convert(any(ClientVendorDto.class), any(ClientVendor.class));
        verify(clientVendorRepository, times(0)).save(any(ClientVendor.class));
    }

    @Test
    void testListAllClientVendor() {
        when(clientVendorRepository.findAll()).thenReturn(clientVendorList);
        when(mapperUtil.convert(clientVendorList.get(0), new ClientVendorDto())).thenReturn(clientVendorDtoList.get(0));
        when(mapperUtil.convert(clientVendorList.get(1), new ClientVendorDto())).thenReturn(clientVendorDtoList.get(1));

        List<ClientVendorDto> result = clientVendorService.listAllClientVendor();

        assertEquals(clientVendorDtoList, result);
        verify(clientVendorRepository, times(1)).findAll();
        verify(mapperUtil, times(2)).convert(any(ClientVendor.class), any(ClientVendorDto.class));
    }

    @Test
    void testFindById_Success() {
        Long id = 1L;

        when(clientVendorRepository.findById(id)).thenReturn(Optional.of(clientVendor));
        when(mapperUtil.convert(clientVendor, new ClientVendorDto())).thenReturn(clientVendorDto);

        ClientVendorDto result = clientVendorService.findById(id);

        assertEquals(clientVendorDto, result);
        verify(clientVendorRepository, times(1)).findById(id);
        verify(mapperUtil, times(1)).convert(clientVendor, new ClientVendorDto());
    }

    @Test
    void testFindById_NotFound() {
        Long id = 1L;

        when(clientVendorRepository.findById(id)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ClientVendorNotFoundException.class, () -> {
            clientVendorService.findById(id);
        });

        String expectedMessage = "clientVendor can not found with id: " + id;
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
        verify(clientVendorRepository, times(1)).findById(id);
        verify(mapperUtil, times(0)).convert(any(ClientVendor.class), any(ClientVendorDto.class));
    }


    private List<ClientVendor> getClientVendorList () {

        List<ClientVendor> clientVendorList = new ArrayList<>();
        clientVendorList.add(clientvendor1);
        clientVendorList.add(clientVendor2);
        return clientVendorList;
    }

    private List<ClientVendorDto> getClientVendorDtoList () {

        List<ClientVendorDto> clientVendorDtoList = new ArrayList<>();
        clientVendorDtoList.add(clientVendorDto1);
        clientVendorDtoList.add(clientVendorDto2);
        return clientVendorDtoList;
    }
    @Test
    public void listAllClientVendor () {

        List<ClientVendor> clientVendorRepositoryAll = clientVendorRepository.findAll();
        List<ClientVendor> clientVendorRepositoryAll2 = clientVendorRepository.findAll();


        when(clientVendorRepository.findAll()).thenReturn(getClientVendorList());
        when(mapperUtil.convert(clientvendor1, new ClientVendorDto())).thenReturn(clientVendorDto1);
        when(mapperUtil.convert(clientVendor2, new ClientVendorDto())).thenReturn(clientVendorDto2);


        List<ClientVendorDto> expectedList = getClientVendorDtoList();
        List<ClientVendorDto> actualList = clientVendorService.listAllClientVendor();


        assertEquals(expectedList, actualList);

    }


    @Test
    void testFindByClientVendorName() {
        String username = "VendorName";

        when(clientVendorRepository.findByClientVendorName(username)).thenReturn(clientVendor);
        when(mapperUtil.convert(clientVendor, new ClientVendorDto())).thenReturn(clientVendorDto);

        ClientVendorDto result = clientVendorService.findByClientVendorName(username);

        assertEquals(clientVendorDto, result);
        verify(clientVendorRepository, times(1)).findByClientVendorName(username);
        verify(mapperUtil, times(1)).convert(clientVendor, new ClientVendorDto());
    }


    @Test
    public void save () {
        ClientVendor clientVendor = new ClientVendor();
        ClientVendorDto clientVendorDto = new ClientVendorDto();

        when(mapperUtil.convert(clientVendorDto, new ClientVendor())).thenReturn(clientVendor);
        when(clientVendorRepository.save(clientVendor)).thenReturn(clientVendor);
        when((mapperUtil.convert(clientVendor, new ClientVendorDto()))).thenReturn(clientVendorDto);

        ClientVendorDto result = clientVendorService.save(clientVendorDto);

        assertEquals(clientVendorDto, result);
        verify(clientVendorRepository, times(1)).save(clientVendor);
    }

    @Test
    public void testListAllByCompanyTitle() {

        String username = "testUser";
        String companyTitle = "Test Company";
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(username);
        SecurityContextHolder.setContext(securityContext);

        User user = new User();
        Company company = new Company();
        company.setTitle(companyTitle);
        user.setCompany(company);
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        ClientVendor clientVendor1 = new ClientVendor();
        clientVendor1.setId(1L);
        clientVendor1.setClientVendorName("Vendor 1");

        ClientVendor clientVendor2 = new ClientVendor();
        clientVendor2.setId(2L);
        clientVendor2.setClientVendorName("Vendor 2");

        ClientVendorDto clientVendorDto1 = new ClientVendorDto();
        clientVendorDto1.setId(1L);
        clientVendorDto1.setClientVendorName("Vendor 1");

        ClientVendorDto clientVendorDto2 = new ClientVendorDto();
        clientVendorDto2.setId(2L);
        clientVendorDto2.setClientVendorName("Vendor 2");

        when(clientVendorRepository.findAllByCompanyTitleAndIsDeletedOrderByClientVendorName(companyTitle, false))
                .thenReturn(Arrays.asList(clientVendor1, clientVendor2));
        when(mapperUtil.convert(clientVendor1, new ClientVendorDto())).thenReturn(clientVendorDto1);
        when(mapperUtil.convert(clientVendor2, new ClientVendorDto())).thenReturn(clientVendorDto2);
        when(invoiceService.existByClientVendorId(1L)).thenReturn(true);
        when(invoiceService.existByClientVendorId(2L)).thenReturn(false);

        List<ClientVendorDto> result = clientVendorService.listAllByCompanyTitle();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.get(0).getHasInvoice());
        assertFalse(result.get(1).getHasInvoice());
        verify(userRepository, times(1)).findByUsername(username);
        verify(clientVendorRepository, times(1)).findAllByCompanyTitleAndIsDeletedOrderByClientVendorName(companyTitle, false);
        verify(mapperUtil, times(1)).convert(clientVendor1, new ClientVendorDto());
        verify(mapperUtil, times(1)).convert(clientVendor2, new ClientVendorDto());
        verify(invoiceService, times(1)).existByClientVendorId(1L);
        verify(invoiceService, times(1)).existByClientVendorId(2L);


    }


    @Test
    public void update () {
        Long id = 1L;
        ClientVendor clientVendor = new ClientVendor();
        ClientVendorDto clientVendorDto = new ClientVendorDto();
        when(clientVendorRepository.findById(id)).thenReturn(Optional.of(clientVendor));
        when(mapperUtil.convert(clientVendorDto, new ClientVendor())).thenReturn(clientVendor);
        when(clientVendorRepository.save(clientVendor)).thenReturn(clientVendor);
        when((mapperUtil.convert(clientVendor, new ClientVendorDto()))).thenReturn(clientVendorDto);

        ClientVendorDto result = clientVendorService.update(clientVendorDto);

        assertEquals(clientVendorDto, result);
        verify(clientVendorRepository, times(1)).findById(id);
        verify(clientVendorRepository, times(1)).save(clientVendor);
    }


    @Test
    public void testDelete() {
        Long id = 1L;
        doNothing().when(clientVendorRepository).deleteById(id);

        clientVendorService.delete(id);

        verify(clientVendorRepository, times(1)).deleteById(id);
    }



}
