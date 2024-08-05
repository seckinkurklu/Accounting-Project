package com.cydeo.service;

import com.cydeo.dto.InvoiceProductDto;
import com.cydeo.entity.InvoiceProduct;
import com.cydeo.enums.InvoiceStatus;
import org.springframework.stereotype.Service;

import java.util.List;


public interface InvoiceProductService {
    List<InvoiceProductDto> getAllInvoiceProducts();
    InvoiceProductDto getInvoiceProductById(Long id);
    void save(InvoiceProductDto invoiceProductDto);

    List<InvoiceProductDto> getAllInvoiceProductsById(Long id);
    void delete(Long id);
    boolean existsByProductIdAndIsDeleted(Long id,boolean isDeleted);
//   boolean existsByInvoiceIdAndIsDeleted(Long id,boolean isDeleted);
    void deleteByInvoiceId(Long id);



    void checkForLowQuantityAlert(Long id);

    List<InvoiceProductDto> findAllApprovedInvoiceInvoiceProduct(InvoiceStatus invoiceStatus);


}



