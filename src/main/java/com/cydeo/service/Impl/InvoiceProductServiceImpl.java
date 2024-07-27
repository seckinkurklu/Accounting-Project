package com.cydeo.service.impl;

import com.cydeo.entity.InvoiceProduct;
import com.cydeo.repository.InvoiceProductRepository;
import com.cydeo.service.InvoiceProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvoiceProductServiceImpl implements InvoiceProductService {

    private final InvoiceProductRepository invoiceProductRepository;

    public InvoiceProductServiceImpl(InvoiceProductRepository invoiceProductRepository) {
        this.invoiceProductRepository = invoiceProductRepository;
    }


    @Override
    public List<InvoiceProduct> getAllInvoiceProducts() {
        return invoiceProductRepository.findAll();
    }

    @Override
    public InvoiceProduct getInvoiceProductById(Long id) {
        return invoiceProductRepository.findById(id).orElse(null);
    }

    @Override
    public InvoiceProduct createInvoiceProduct(InvoiceProduct invoiceProduct) {
        return createInvoiceProduct(invoiceProduct);
    }

    @Override
    public InvoiceProduct updateInvoiceProduct(Long id, InvoiceProduct invoiceProduct) {
        return null;
    }

    @Override
    public void deleteInvoiceProduct(Long id) {
        deleteInvoiceProduct(id);

    }
}
