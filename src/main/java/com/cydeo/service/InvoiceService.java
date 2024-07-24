package com.cydeo.service;

import com.cydeo.converter.InvoiceProductDTOConverter;
import com.cydeo.entity.Invoice;
import com.cydeo.entity.InvoiceProduct;

import java.util.List;

public interface InvoiceService {
    List<Invoice> getAllInvoices();
    Invoice getInvoiceById(Long id);
    Invoice createInvoice(Invoice invoice);
    Invoice upodateInvoice(Long id,Invoice invoice);
    void deleteInvoice(Long id);
}
