package com.cydeo.service.impl;

import com.cydeo.converter.ClientVendorDTOConverter;
import com.cydeo.dto.*;
import com.cydeo.entity.*;
import com.cydeo.enums.InvoiceStatus;
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
    public List<InvoiceDto> getAllInvoices() {
        List<Invoice> all = invoiceRepository.findAll();
        return all.stream().map(p->mapperUtil.convert(p, new InvoiceDto())).toList();
    }

    @Override
    public InvoiceDto getInvoiceById(Long id) {
        return mapperUtil.convert(invoiceRepository.findById(id).get(), new InvoiceDto());
    }

    @Override
    public InvoiceDto createInvoice(Invoice invoice) {
        return mapperUtil.convert(invoiceRepository.save(invoice), new InvoiceDto());
    }

    @Override
    public InvoiceDto updateInvoice(Long id,Invoice invoice) {
        return mapperUtil.convert(invoiceRepository.findById(id).get(), new InvoiceDto());
    }

    @Override
    public void deleteInvoice(Long id) {
        invoiceRepository.deleteById(id);
    }

    //for US-49
    @Override
    public List<InvoiceDto> listAllPurchaseInvoice() {
        UserDto loggedInUser = securityService.getLoggedInUser();
        String companyTitle = loggedInUser.getCompany().getTitle();
        //Purchase Invoices should be sorted by their invoice no in descending order (latest invoices should be at the top).
        List<Invoice> invoices = invoiceRepository.findAllByInvoiceTypeAndCompany_TitleOrderByInvoiceNoDesc(InvoiceType.PURCHASE, companyTitle );

        List<InvoiceDto> invoiceDtoList = invoices.stream().map(p -> mapperUtil.convert(p, new InvoiceDto())).toList();

        invoiceDtoList = invoiceDtoList.stream().map(p -> {
            Long id = mapperUtil.convert(p, new Invoice()).getId();
            InvoiceProduct invoiceProduct = invoiceProductRepository.findByInvoice_Id(id);

            int quantity = invoiceProduct.getQuantity();

            BigDecimal priceTotal = invoiceProduct.getPrice().multiply(BigDecimal.valueOf(quantity));
            p.setPrice(priceTotal);

            BigDecimal tax = BigDecimal.valueOf(invoiceProduct.getTax());
            BigDecimal totalTax = tax.multiply(priceTotal).divide(BigDecimal.valueOf(100));
            p.setTax(totalTax);
            p.setTotal(priceTotal.add(totalTax));
            return p;
        }).toList();

return invoiceDtoList;
    }
    //for US-49
    @Override
    public List<InvoiceDto> listAllSalesInvoice() {
        UserDto loggedInUser = securityService.getLoggedInUser();
        String companyTitle = loggedInUser.getCompany().getTitle();
        List<Invoice> invoices = invoiceRepository.findAllByInvoiceTypeAndCompany_TitleOrderByInvoiceNoDesc(InvoiceType.SALES, companyTitle);
        return invoices.stream().map(p->mapperUtil.convert(p, new InvoiceDto())).toList();
    }
    @Override
    public InvoiceDto save(InvoiceDto invoiceDto) {
        Long id = invoiceDto.getClientVendor().getId();// vendor id (th:value="${vendor.id}")--th:field="*{clientVendor}
        int quantity = invoiceProductRepository.findByInvoice_Id(id).getQuantity();
        BigDecimal price = invoiceProductRepository.findByInvoice_Id(id).getPrice();
        int tax = invoiceProductRepository.findByInvoice_Id(id).getTax();

        invoiceDto.setTax(BigDecimal.valueOf(tax));
        ClientVendor clientVendor = clientVendorRepository.getReferenceById(id);
        Invoice invoice = mapperUtil.convert(invoiceDto, new Invoice());

        String invoiceNo = listAllInvoice().get(listAllInvoice().size()-1);
        int i = Integer.parseInt(invoiceNo);

        invoice.setInvoiceNo(newInvoiceNo());// set inVoiceNo
        invoice.setDate(LocalDate.now()); // Set date
        invoice.setClientVendor(clientVendor); // set Vendor
        invoice.setInvoiceType(InvoiceType.PURCHASE);
        invoice.setInvoiceStatus(InvoiceStatus.AWAITING_APPROVAL);
        UserDto loggedInUser = securityService.getLoggedInUser();
        CompanyDto company = loggedInUser.getCompany();

        invoice.setCompany(mapperUtil.convert(company, new Company()));

        Invoice saved = invoiceRepository.save(invoice);
        return mapperUtil.convert(saved, new InvoiceDto());
    }

    private List<String> listAllInvoice() {
        List<String> allInvoiceNo = invoiceRepository.findAllByInvoiceType(InvoiceType.PURCHASE).stream().map(p->p.getInvoiceNo()).toList();

        List<String> stringList = allInvoiceNo.stream().map(p -> p.replace("P-", "")).sorted().toList();

        return stringList;
    }
    @Override
    public String newInvoiceNo(){
        String invoiceNo = listAllInvoice().get(listAllInvoice().size()-1);
        int i = Integer.parseInt(invoiceNo);
        return String.format("P-%03d", i+1);
    }

    @Override
    public Long getId(String invoiceNo) {
        return invoiceRepository.findInvoiceByInvoiceNo(invoiceNo).getId();
    }

}
