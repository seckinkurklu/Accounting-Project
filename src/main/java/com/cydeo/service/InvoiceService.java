package com.cydeo.service;

import com.cydeo.dto.InvoiceDto;
import com.cydeo.entity.Invoice;

import java.util.List;

public interface InvoiceService {
    List<InvoiceDto> getAllInvoices();
    InvoiceDto getInvoiceById(Long id);
    InvoiceDto createInvoice(Invoice invoice);
    InvoiceDto updateInvoice(Long id,Invoice invoice);
    void deleteInvoice(Long id);

    // should use DTO class in Service
    // for US-49
    List<InvoiceDto> listAllPurchaseInvoice();
    List<InvoiceDto> listAllSalesInvoice();

    InvoiceDto save(InvoiceDto invoiceDto);

    String newInvoiceNo();

    Long getId(String invoiceNo);
}
