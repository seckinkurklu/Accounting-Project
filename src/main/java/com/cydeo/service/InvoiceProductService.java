package com.cydeo.service;

import com.cydeo.entity.InvoiceProduct;
import net.bytebuddy.implementation.auxiliary.AuxiliaryType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface InvoiceProductService {
    List<InvoiceProduct> getAllInvoiceProducts();
    InvoiceProduct getInvoiceProductById(Long id);
    InvoiceProduct createInvoiceProduct(InvoiceProduct invoiceProduct);
    InvoiceProduct updateInvoiceProduct(Long id, InvoiceProduct invoiceProduct);
    void deleteInvoiceProduct(Long id);

}
