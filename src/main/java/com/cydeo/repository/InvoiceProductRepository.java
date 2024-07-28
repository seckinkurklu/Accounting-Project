package com.cydeo.repository;

import com.cydeo.entity.Invoice;
import com.cydeo.entity.InvoiceProduct;
import com.cydeo.entity.Product;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InvoiceProductRepository extends JpaRepository<InvoiceProduct, Long> {


   List<InvoiceProduct> findAllByInvoice(Invoice invoice);

   InvoiceProduct findByInvoice(Invoice invoice);

//   InvoiceProduct findByInvoice_Id(Long id);
   List<InvoiceProduct> findAllByInvoice_Id(Long id);



}
