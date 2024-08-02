package com.cydeo.service;

import com.cydeo.dto.InvoiceProductDto;

import java.util.List;
import java.util.Map;

public interface ReportingService {


    Map<String,Double> getMonthlyProfitLossByCompany();

   List<InvoiceProductDto> getInvoiceProductList();
}
