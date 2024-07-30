package com.cydeo.service;

import com.cydeo.dto.InvoiceDto;
import com.cydeo.entity.Invoice;

import java.util.List;

public interface InvoiceService {
    List<Invoice> getAllInvoices();
    InvoiceDto getInvoiceById(Long id);
    Invoice createInvoice(Invoice invoice);
    Invoice updateInvoice(Long id,Invoice invoice);
    void deleteInvoice(Long id);

    // should use DTO class in Service
    // for US-49
    List<InvoiceDto> listAllPurchaseInvoice();
    List<InvoiceDto> listAllSalesInvoice();

    InvoiceDto save(InvoiceDto invoiceDto);

    String newInvoiceNo();

    Long getId(String invoiceNo);
    void approve(Long invoiceId);
    List<InvoiceDto> listLastThreeApprovedSalesInvoices();
}
