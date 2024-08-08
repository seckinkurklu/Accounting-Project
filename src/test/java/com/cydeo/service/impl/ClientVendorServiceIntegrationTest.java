package com.cydeo.service.impl;

import java.util.List;

import com.cydeo.dto.ClientVendorDto;
import com.cydeo.entity.ClientVendor;
import com.cydeo.entity.Company;
import com.cydeo.entity.User;
import com.cydeo.exception.ClientVendorNotFoundException;
import com.cydeo.exception.UserNotFoundException;
import com.cydeo.repository.ClientVendorRepository;
import com.cydeo.repository.UserRepository;

import com.cydeo.service.s_impl.ClientVendorServiceImpl;
import com.cydeo.util.MapperUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@Transactional
public class ClientVendorServiceIntegrationTest {

    @Autowired
    private ClientVendorServiceImpl clientVendorService;

    @Autowired
    private ClientVendorRepository clientVendorRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MapperUtil mapperUtil;

    @BeforeEach
    void setUp() {
        // Create and save a Company
        Company company = new Company();
        company.setTitle("Test Company");

        // Create and save a User with the Company
        User user = new User();
        user.setUsername("testUser");
        user.setCompany(company);
        userRepository.save(user);

        // Set the security context to simulate an authenticated user
        SecurityContextHolder.getContext().setAuthentication(
                new org.springframework.security.authentication.UsernamePasswordAuthenticationToken("testUser", "password")
        );
    }

    @Test
    void testSaveClientVendor() {
        ClientVendorDto clientVendorDto = new ClientVendorDto();
        clientVendorDto.setClientVendorName("VendorName");

        // Save ClientVendor
        ClientVendorDto savedClientVendorDto = clientVendorService.save(clientVendorDto);

        // Fetch ClientVendor from the repository
        ClientVendor clientVendor = clientVendorRepository.findById(savedClientVendorDto.getId())
                .orElseThrow(() -> new ClientVendorNotFoundException("ClientVendor not found"));

        // Assert results
        assertThat(clientVendor.getClientVendorName()).isEqualTo("VendorName");
    }

    @Test
    void testFindById() {
        // Prepare and save a ClientVendor
        ClientVendor clientVendor = new ClientVendor();
        clientVendor.setName("VendorName");
        clientVendor = clientVendorRepository.save(clientVendor);

        // Fetch ClientVendorDto by ID
        ClientVendorDto clientVendorDto = clientVendorService.findById(clientVendor.getId());

        // Assert results
        assertThat(clientVendorDto.getClientVendorName()).isEqualTo("VendorName");
    }

    @Test
    void testUpdate() {
        // Prepare and save a ClientVendor
        ClientVendor clientVendor = new ClientVendor();
        clientVendor.setName("VendorName");
        clientVendor = clientVendorRepository.save(clientVendor);

        ClientVendorDto clientVendorDto = new ClientVendorDto();
        clientVendorDto.setId(clientVendor.getId());
        clientVendorDto.setClientVendorName("UpdatedVendorName");

        // Update ClientVendor
        ClientVendorDto updatedClientVendorDto = clientVendorService.update(clientVendorDto);

        // Fetch updated ClientVendor from the repository
        ClientVendor updatedClientVendor = clientVendorRepository.findById(clientVendor.getId())
                .orElseThrow(() -> new ClientVendorNotFoundException("ClientVendor not found"));

        // Assert results
        assertThat(updatedClientVendor.getClientVendorName()).isEqualTo("UpdatedVendorName");
    }

    @Test
    void testDelete() {
        // Prepare and save a ClientVendor
        ClientVendor clientVendor = new ClientVendor();
        clientVendor.setName("VendorName");
        clientVendor = clientVendorRepository.save(clientVendor);

        // Delete ClientVendor
        clientVendorService.delete(clientVendor.getId());

        // Fetch deleted ClientVendor from the repository
        ClientVendor deletedClientVendor = clientVendorRepository.findById(clientVendor.getId())
                .orElseThrow(() -> new ClientVendorNotFoundException("ClientVendor not found"));

        // Assert results
        assertThat(deletedClientVendor.getIsDeleted()).isTrue();
    }

    @Test
    void testListAllClientVendor() {
        // Prepare and save ClientVendors
        ClientVendor clientVendor1 = new ClientVendor();
        clientVendor1.setName("Vendor1");
        clientVendorRepository.save(clientVendor1);

        ClientVendor clientVendor2 = new ClientVendor();
        clientVendor2.setName("Vendor2");
        clientVendorRepository.save(clientVendor2);

        // Fetch all ClientVendorDto
        List<ClientVendorDto> clientVendorDtoList = clientVendorService.listAllClientVendor();

        // Assert results
        assertThat(clientVendorDtoList).hasSize(2);
        assertThat(clientVendorDtoList.get(0).getClientVendorName()).isEqualTo("Vendor1");
        assertThat(clientVendorDtoList.get(1).getClientVendorName()).isEqualTo("Vendor2");
    }

    @Test
    void testListAllByCompanyTitle() {
        // Prepare and save a ClientVendor
        ClientVendor clientVendor = new ClientVendor();
        clientVendor.setName("VendorName");
        clientVendor.setCompany(userRepository.findByUsername("testUser").get().getCompany());
        clientVendorRepository.save(clientVendor);

        // Fetch ClientVendorDto by company title
        List<ClientVendorDto> clientVendorDtoList = clientVendorService.listAllByCompanyTitle();

        // Assert results
        assertThat(clientVendorDtoList).isNotEmpty();
        assertThat(clientVendorDtoList.get(0).getClientVendorName()).isEqualTo("VendorName");
    }
}
