package com.cydeo.service.impl;

import com.cydeo.converter.ClientVendorDTOConverter;
import com.cydeo.dto.ClientVendorDto;
import com.cydeo.dto.InvoiceDto;
import com.cydeo.dto.UserDto;
import com.cydeo.entity.ClientVendor;
import com.cydeo.entity.Invoice;
import com.cydeo.entity.InvoiceProduct;
import com.cydeo.entity.User;
import com.cydeo.enums.InvoiceType;
import com.cydeo.repository.ClientVendorRepository;
import com.cydeo.repository.InvoiceProductRepository;
import com.cydeo.repository.InvoiceRepository;
import com.cydeo.repository.UserRepository;
import com.cydeo.service.InvoiceService;
import com.cydeo.service.SecurityService;
import com.cydeo.util.MapperUtil;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final MapperUtil mapperUtil;
    private final UserRepository userRepository;
    private final ClientVendorDTOConverter clientVendorDTOConverter;
    private final ClientVendorRepository clientVendorRepository;
    private final InvoiceProductRepository invoiceProductRepository;
    private final SecurityService securityService;
    public InvoiceServiceImpl(InvoiceRepository invoiceRepository, MapperUtil mapperUtil, UserRepository userRepository, ClientVendorDTOConverter clientVendorDTOConverter, ClientVendorRepository clientVendorRepository, InvoiceProductRepository invoiceProductRepository, SecurityService securityService) {
        this.invoiceRepository = invoiceRepository;
        this.mapperUtil = mapperUtil;
        this.userRepository = userRepository;
        this.clientVendorDTOConverter = clientVendorDTOConverter;
        this.clientVendorRepository = clientVendorRepository;
        this.invoiceProductRepository = invoiceProductRepository;
        this.securityService = securityService;
    }

    @Override
    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }

    @Override
    public Invoice getInvoiceById(Long id) {
        return invoiceRepository.findById(id).get();
    }

    @Override
    public Invoice createInvoice(Invoice invoice) {
        return invoiceRepository.save(invoice);
    }

    @Override
    public Invoice upodateInvoice(Long id,Invoice invoice) {
        Invoice existingInvoice = invoiceRepository.findById(id);
        return null;
    }

    @Override
    public void deleteInvoice(Long id) {
        invoiceRepository.deleteById(getInvoiceById(id));

    }
}
