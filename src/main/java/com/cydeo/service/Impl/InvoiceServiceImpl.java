package com.cydeo.service.impl;

import com.cydeo.converter.ClientVendorDTOConverter;
import com.cydeo.dto.ClientVendorDto;
import com.cydeo.dto.InvoiceDto;
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

    public InvoiceServiceImpl(InvoiceRepository invoiceRepository, MapperUtil mapperUtil, UserRepository userRepository, ClientVendorDTOConverter clientVendorDTOConverter, ClientVendorRepository clientVendorRepository, InvoiceProductRepository invoiceProductRepository) {
        this.invoiceRepository = invoiceRepository;
        this.mapperUtil = mapperUtil;
        this.userRepository = userRepository;
        this.clientVendorDTOConverter = clientVendorDTOConverter;
        this.clientVendorRepository = clientVendorRepository;
        this.invoiceProductRepository = invoiceProductRepository;
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
        //Invoice existingInvoice = invoiceRepository.findById(id);
        return null;
    }

    @Override
    public void deleteInvoice(Long id) {
        invoiceRepository.deleteById(getInvoiceById(id));

    }
    //for US-49
    @Override
    public List<InvoiceDto> listAllPurchaseInvoice() {
        String username= SecurityContextHolder.getContext().getAuthentication().getName();
        User byUsername = userRepository.findByUsername(username);
        List<Invoice> invoices = invoiceRepository.findAllByInvoiceTypeAndCompany(InvoiceType.PURCHASE, byUsername.getCompany() );
     List<InvoiceDto> invoiceDtoList = invoices.stream().map(p -> mapperUtil.convert(p, new InvoiceDto())).toList();

//        List<InvoiceDto> invoiceDtoList = invoices.stream().map(invoice -> {
//            InvoiceDto dto = new InvoiceDto();
//            dto.setId(invoice.getId());
//            dto.setInvoiceNo(invoice.getInvoiceNo());
//            dto.setClientVendor(mapperUtil.convert(invoice.getClientVendor(), new ClientVendorDto()));
//            dto.setDate(invoice.getDate());
//
//            List<InvoiceProduct> listProductsRelatedInvoice = invoiceProductRepository.findAllByInvoice(invoice);
//            InvoiceProduct byInvoice = invoiceProductRepository.findByInvoice(invoice);
//            BigDecimal totalPrice = listProductsRelatedInvoice.stream()
//                    .map(InvoiceProduct::getPrice)
//                    .filter(price -> price != null)
//                    .reduce(BigDecimal.ZERO, BigDecimal::add);
//
//            Integer totalTax = listProductsRelatedInvoice.stream()
//                    .map(InvoiceProduct::getTax)
//                    .filter(tax -> tax != null)
//                    .reduce(Integer::sum).get();
//            dto.setPrice(byInvoice.getPrice());
//            dto.setTax(BigDecimal.valueOf(totalTax));
//            dto.setTotal(totalPrice);
//            return dto;
//        }).toList();
//
//        // print dto to test
//        invoiceDtoList.forEach(dto -> {
//            System.out.println("InvoiceNo: " + dto.getInvoiceNo());
//            System.out.println("Total Price: " + dto.getTotal());
//            System.out.println("Total Tax: " + dto.getTax());
//        });
        return invoiceDtoList;
    }
    //for US-49
    @Override
    public List<InvoiceDto> listAllSalesInvoice() {
        String username= SecurityContextHolder.getContext().getAuthentication().getName();
        User byUsername = userRepository.findByUsername(username);
        List<Invoice> invoices = invoiceRepository.findAllByInvoiceTypeAndCompany(InvoiceType.SALES, byUsername.getCompany());
        return invoices.stream().map(p->mapperUtil.convert(p, new InvoiceDto())).toList();
    }

    @Override
    public InvoiceDto save(InvoiceDto invoiceDto) {
        Long id = invoiceDto.getClientVendor().getId();// vendor id (th:value="${vendor.id}")--th:field="*{clientVendor}

        ClientVendor clientVendor = clientVendorRepository.getReferenceById(id);
        Invoice invoice = mapperUtil.convert(invoiceDto, new Invoice());

        String invoiceNo = listAllInvoice().get(listAllInvoice().size()-1);
        int i = Integer.parseInt(invoiceNo);

        invoice.setInvoiceNo("P-" + (i+1));// set inVoiceNo
        invoice.setDate(LocalDate.now()); // Set date
        invoice.setClientVendor(clientVendor); // set Vendor
        invoice.setInvoiceType(InvoiceType.PURCHASE);
        String username= SecurityContextHolder.getContext().getAuthentication().getName();
        User byUsername = userRepository.findByUsername(username);
//        System.out.println("================User's company "); // just to verify
//        System.out.println(byUsername.getCompany().toString());// just to verify
        invoice.setCompany(byUsername.getCompany());

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
