package com.cydeo.service;

import com.cydeo.dto.InvoiceProductDto;
import com.cydeo.entity.InvoiceProduct;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface InvoiceProductService {
    List<InvoiceProductDto> getAllInvoiceProducts();
    List<InvoiceProductDto> getAllInvoiceProductsById(Long id);
    InvoiceProductDto getInvoiceProductById(Long id);
    InvoiceProductDto createInvoiceProduct(InvoiceProduct invoiceProduct);
    InvoiceProductDto updateInvoiceProduct(Long id, InvoiceProduct invoiceProduct);
    void deleteInvoiceProduct(Long id);

    void save(InvoiceProductDto invoiceProductDTO);

}



