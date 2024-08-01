package com.cydeo.service.s_impl;

import com.cydeo.repository.InvoiceProductRepository;
import com.cydeo.service.InvoiceProductService;
import com.cydeo.service.ReportService;
import com.cydeo.service.SecurityService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Service
public class ReportServiceImpl implements ReportService {
private final InvoiceProductRepository productRepository;
    private final InvoiceProductRepository invoiceProductRepository;
private final SecurityService securityService;
    public ReportServiceImpl(InvoiceProductRepository productRepository, InvoiceProductRepository invoiceProductRepository, SecurityService securityService) {
        this.productRepository = productRepository;
        this.invoiceProductRepository = invoiceProductRepository;
        this.securityService = securityService;
    }

    @Override
    public Map<String, Double> getMonthlyProfitLossByCompany() {
        String companyTitle= securityService.getLoggedInUser().getCompany().getTitle();
        List<Object[]> results = invoiceProductRepository.dateAndProfitLossByCompanyTitle(companyTitle);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Map<String, Double> monthlySums = new HashMap<>();

        for (Object[] result : results) {
            LocalDate date = LocalDate.parse(result[0].toString(), formatter);
            double profitLoss = Double.parseDouble(result[1].toString());
            String month = date.getYear() + " " + date.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH);

            monthlySums.put(month, monthlySums.getOrDefault(month, 0.0) + profitLoss);
        }

        return monthlySums;
    }
}
