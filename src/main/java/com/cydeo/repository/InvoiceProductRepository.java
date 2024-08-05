package com.cydeo.repository;

import com.cydeo.entity.Invoice;
import com.cydeo.entity.InvoiceProduct;
import com.cydeo.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface InvoiceProductRepository extends JpaRepository<InvoiceProduct, Long> {


   List<InvoiceProduct> findAllByInvoice(Invoice invoice);
   List<InvoiceProduct> findAllInvoiceProductsByInvoiceIdAndIsDeletedFalse(Long invoiceId);

   InvoiceProduct findByInvoice(Invoice invoice);

//   InvoiceProduct findByInvoice_Id(Long id);
   List<InvoiceProduct> findAllByInvoice_Id(Long id);
   InvoiceProduct findByIdAndIsDeleted(Long id, Boolean isDeleted);

   List<InvoiceProduct> findAllByIsDeletedFalse();
boolean existsByInvoiceIdAndIsDeleted(Long invoiceId, Boolean isDeleted);

   boolean existsByProductIdAndIsDeleted(Long id, boolean isDeleted);

}
