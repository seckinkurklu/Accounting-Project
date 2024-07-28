package com.cydeo.service;

import com.cydeo.dto.InvoiceProductDto;
import com.cydeo.entity.InvoiceProduct;
import org.springframework.stereotype.Service;

import java.util.List;


public interface InvoiceProductService {
    List<InvoiceProductDto> getAllInvoiceProducts();
    InvoiceProductDto getInvoiceProductById(Long id);
    void createInvoiceProduct(InvoiceProductDto invoiceProductDto);
    InvoiceProduct updateInvoiceProduct(Long id, InvoiceProduct invoiceProduct);
    void deleteInvoiceProduct(Long id);
    void save(InvoiceProductDto invoiceProductDTO);

}
