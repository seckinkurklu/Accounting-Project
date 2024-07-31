package com.cydeo.service.s_impl;

import com.cydeo.converter.ClientVendorDTOConverter;
import com.cydeo.dto.InvoiceDto;
import com.cydeo.dto.InvoiceProductDto;
import com.cydeo.dto.ProductDto;
import com.cydeo.dto.UserDto;
import com.cydeo.entity.ClientVendor;
import com.cydeo.entity.Invoice;
import com.cydeo.entity.InvoiceProduct;
import com.cydeo.entity.User;
import com.cydeo.enums.InvoiceStatus;
import com.cydeo.enums.InvoiceType;

import com.cydeo.exception.InvoiceNotFoundException;
import com.cydeo.repository.ClientVendorRepository;
import com.cydeo.repository.InvoiceProductRepository;
import com.cydeo.repository.InvoiceRepository;
import com.cydeo.repository.UserRepository;
import com.cydeo.service.InvoiceProductService;
import com.cydeo.service.InvoiceService;
import com.cydeo.service.ProductService;
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
    private final InvoiceProductService invoiceProductService;
    private final ProductService productService;

    public InvoiceServiceImpl(InvoiceRepository invoiceRepository, MapperUtil mapperUtil, UserRepository userRepository, ClientVendorDTOConverter clientVendorDTOConverter, ClientVendorRepository clientVendorRepository, InvoiceProductRepository invoiceProductRepository, SecurityService securityService, InvoiceProductService invoiceProductService, ProductService productService) {
        this.invoiceRepository = invoiceRepository;
        this.mapperUtil = mapperUtil;
        this.userRepository = userRepository;
        this.clientVendorDTOConverter = clientVendorDTOConverter;
        this.clientVendorRepository = clientVendorRepository;
        this.invoiceProductRepository = invoiceProductRepository;
        this.securityService = securityService;
        this.invoiceProductService = invoiceProductService;
        this.productService = productService;
    }

    @Override
    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }

    @Override
    public InvoiceDto getInvoiceById(Long id) {
        Invoice invoice = invoiceRepository.findById(id).get();
        return mapperUtil.convert(invoice,new InvoiceDto());
    }

    @Override
    public Invoice createInvoice(Invoice invoice) {
        return invoiceRepository.save(invoice);
    }

    @Override
    public Invoice updateInvoice(Long id, Invoice invoice) {
        return null;
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
        List<Invoice> invoices = invoiceRepository.findAllByInvoiceTypeAndCompany_TitleOrderByInvoiceNoDesc(InvoiceType.PURCHASE, companyTitle);

        List<InvoiceDto> invoiceDtoList = invoices.stream().map(p -> mapperUtil.convert(p, new InvoiceDto())).toList();

        invoiceDtoList = invoiceDtoList.stream().map(p -> {
            Long id = mapperUtil.convert(p, new Invoice()).getId();
            InvoiceProduct invoiceProduct = invoiceProductRepository.findById(id).get();

            int quantity = invoiceProduct.getQuantity();

            BigDecimal priceTotal = invoiceProduct.getPrice().multiply(BigDecimal.valueOf(quantity));
            p.setPrice(priceTotal);

            BigDecimal tax = invoiceProduct.getTax();
            BigDecimal totalTax = tax.multiply(priceTotal).divide(BigDecimal.valueOf(100));
            p.setTax(totalTax);
            //p.setTotal(totalPriceWithTax);
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
        return invoices.stream().map(p -> mapperUtil.convert(p, new InvoiceDto())).toList();
    }

    @Override
    public InvoiceDto save(InvoiceDto invoiceDto) {
        Long id = invoiceDto.getClientVendor().getId();// vendor id (th:value="${vendor.id}")--th:field="*{clientVendor}

        ClientVendor clientVendor = clientVendorRepository.getReferenceById(id);
        Invoice invoice = mapperUtil.convert(invoiceDto, new Invoice());

        String invoiceNo = listAllInvoice().get(listAllInvoice().size() - 1);
        int i = Integer.parseInt(invoiceNo);

        invoice.setInvoiceNo(newInvoiceNo());// set inVoiceNo
        invoice.setDate(LocalDate.now()); // Set date
        invoice.setClientVendor(clientVendor); // set Vendor
        invoice.setInvoiceType(InvoiceType.PURCHASE);
        invoice.setInvoiceStatus(InvoiceStatus.AWAITING_APPROVAL);
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User byUsername = userRepository.findByUsername(username);
//        System.out.println("================User's company "); // just to verify
//        System.out.println(byUsername.getCompany().toString());// just to verify
        invoice.setCompany(byUsername.getCompany());

        Invoice saved = invoiceRepository.save(invoice);
        return mapperUtil.convert(saved, new InvoiceDto());
    }

    private List<String> listAllInvoice() {
        List<String> allInvoiceNo = invoiceRepository.findAllByInvoiceType(InvoiceType.PURCHASE).stream().map(p -> p.getInvoiceNo()).toList();

        List<String> stringList = allInvoiceNo.stream().map(p -> p.replace("P-", "")).sorted().toList();

        return stringList;
    }

    @Override
    public String newInvoiceNo() {
        String invoiceNo = listAllInvoice().get(listAllInvoice().size() - 1);
        int i = Integer.parseInt(invoiceNo);
        return String.format("P-%03d", i + 1);
    }

    @Override
    public Long getId(String invoiceNo) {
        return invoiceRepository.findInvoiceByInvoiceNo(invoiceNo).getId();
    }

    @Override
    public void approve(Long invoiceId) {
        Invoice invoiceToApprove = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new InvoiceNotFoundException("Invoice can not found with id: " + invoiceId));

        List<InvoiceProductDto> invoiceProductList = (List<InvoiceProductDto>) invoiceProductService.getInvoiceProductById(invoiceToApprove.getId());

        if (invoiceToApprove.getInvoiceType() == InvoiceType.PURCHASE) {
            savePurchaseInvoiceToProductProfitLoss(invoiceProductList);
            increaseProductRemainingQuantity(invoiceProductList);
        }

        invoiceToApprove.setInvoiceStatus(InvoiceStatus.APPROVED);
        invoiceToApprove.setDate(LocalDate.now());

    }

    @Override
    public void removeInvoiceById(Long id) {
        Optional<Invoice> invoice = invoiceRepository.findById(id);
        if(invoice.isPresent()) {
            invoice.get().setIsDeleted(true);
            invoiceRepository.save(invoice.get());
        }
    }

    public void savePurchaseInvoiceToProductProfitLoss(List<InvoiceProductDto> invoiceProductList) {
        invoiceProductList.forEach(invoiceProduct -> {
            invoiceProduct.setProfitLoss(BigDecimal.ZERO);
            invoiceProduct.setRemainingQuantity(invoiceProduct.getQuantity());


            invoiceProductService.save(invoiceProduct);
        });
    }

    public void increaseProductRemainingQuantity(List<InvoiceProductDto> invoiceProductList) {
        invoiceProductList.forEach(invoiceProductDto -> {
            ProductDto product = invoiceProductDto.getProduct();
            Integer quantity = invoiceProductDto.getQuantity();
            productService.increaseProductQuantityInStock(product.getId(), quantity);
        });
    }


}