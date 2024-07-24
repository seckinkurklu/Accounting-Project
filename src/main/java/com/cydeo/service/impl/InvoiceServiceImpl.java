package com.cydeo.service.impl;

import com.cydeo.entity.Invoice;
import com.cydeo.repository.InvoiceRepository;
import com.cydeo.service.InvoiceService;

import java.util.List;

public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;

    public InvoiceServiceImpl(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    @Override
    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }

    @Override
    public Invoice getInvoiceById(Long id) {
        return invoiceRepository.findById(id);
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
