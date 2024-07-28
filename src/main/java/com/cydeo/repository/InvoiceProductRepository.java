package com.cydeo.repository;

import com.cydeo.entity.Invoice;
import com.cydeo.entity.InvoiceProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvoiceProductRepository extends JpaRepository<InvoiceProduct, Long> {


   List<InvoiceProduct> findAllByInvoice(Invoice invoice);
   List<InvoiceProduct> findAllInvoiceProductsByInvoiceId(Long invoiceId);

   InvoiceProduct findByInvoice(Invoice invoice);

   InvoiceProduct findByInvoice_Id(Long id);

}
