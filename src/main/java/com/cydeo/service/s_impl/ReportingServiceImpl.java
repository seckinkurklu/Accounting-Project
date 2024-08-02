package com.cydeo.service.s_impl;

import com.cydeo.dto.InvoiceProductDto;
import com.cydeo.enums.InvoiceStatus;
import com.cydeo.repository.InvoiceProductRepository;
import com.cydeo.service.InvoiceProductService;
import com.cydeo.service.ReportingService;
import com.cydeo.service.SecurityService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.*;

@Service
public class ReportingServiceImpl implements ReportingService {
    private final InvoiceProductRepository productRepository;
    private final InvoiceProductRepository invoiceProductRepository;
    private final SecurityService securityService;
    private final InvoiceProductService invoiceProductService;

    public ReportingServiceImpl(InvoiceProductRepository productRepository, InvoiceProductRepository invoiceProductRepository, SecurityService securityService, InvoiceProductService invoiceProductService) {
        this.productRepository = productRepository;
        this.invoiceProductRepository = invoiceProductRepository;
        this.securityService = securityService;
        this.invoiceProductService = invoiceProductService;
    }


    @Override
    public Map<String, Double> getMonthlyProfitLossByCompany() {
        String companyTitle = securityService.getLoggedInUser().getCompany().getTitle();
        String invoiceStatus = InvoiceStatus.APPROVED.name(); //convert Enum value to String
        List<Object[]> results = invoiceProductRepository.dateAndProfitLossByCompanyTitle(companyTitle, invoiceStatus);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Map<String, Double> monthlySums = new HashMap<>();

        for (Object[] result : results) {
            LocalDate date = LocalDate.parse(result[0].toString(), formatter);
            double profitLoss = Double.parseDouble(result[1].toString());
            String month = date.getYear() + " " + date.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH);

            monthlySums.put(month, monthlySums.getOrDefault(month, 0.0) + profitLoss);
        }
        //sorted based on date in descending order
        Map<String, Double> sortedMap = new TreeMap<>(Collections.reverseOrder());
        sortedMap.putAll(monthlySums);
        return sortedMap;
    }

    @Override
    public List<InvoiceProductDto> getInvoiceProductList() {
        return invoiceProductService. findAllApprovedInvoiceInvoiceProduct(InvoiceStatus.APPROVED);

    }
}
