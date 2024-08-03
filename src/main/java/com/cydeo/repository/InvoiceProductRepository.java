package com.cydeo.repository;

import com.cydeo.entity.Invoice;
import com.cydeo.entity.InvoiceProduct;
import com.cydeo.enums.InvoiceStatus;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
public interface InvoiceProductRepository extends JpaRepository<InvoiceProduct, Long> {


   List<InvoiceProduct> findAllByInvoice(Invoice invoice);
   List<InvoiceProduct> findAllInvoiceProductsByInvoiceIdAndIsDeletedFalse(Long invoiceId);

   InvoiceProduct findByInvoice(Invoice invoice);

//   InvoiceProduct findByInvoice_Id(Long id);
   List<InvoiceProduct> findAllByInvoice_Id(Long id);
   InvoiceProduct findByIdAndIsDeleted(Long id, Boolean isDeleted);

   List<InvoiceProduct> findAllByIsDeletedFalse();

   boolean existsByProductIdAndIsDeleted(Long id, boolean isDeleted);

   @Query(value = "select i.date, ip.profit_loss from invoices i join invoice_products ip on i.id = ip.invoice_id\n" +
           "join companies c on i.company_id = c.id where c.title=?1 and i.invoice_status =?2", nativeQuery = true)
   List<Object[]> dateAndProfitLossByCompanyTitle(@Param("title") String companyTitle, @Param("invoice_status")String invoiceStatus);

   List<InvoiceProduct> findAllByInvoiceId(Long id);
}
