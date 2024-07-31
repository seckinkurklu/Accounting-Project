package com.cydeo.repository;

import com.cydeo.entity.Company;
import com.cydeo.entity.Invoice;
import com.cydeo.enums.InvoiceStatus;
import com.cydeo.enums.InvoiceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;
@Repository
public interface InvoiceRepository extends JpaRepository<Invoice,Long> {

    List<Invoice> findAll();
    //Invoice findById(Long id);
    Invoice save(Invoice invoice);

    void deleteById(Invoice invoice);

    //for US-49
    //List<Invoice> findAllByInvoiceType_AndCompanyOrderByInvoiceNoDesc(InvoiceType invoiceType, Company company);
    List<Invoice> findAllByInvoiceTypeAndCompany_TitleOrderByInvoiceNoDesc(InvoiceType invoiceType, String companyTitle);
    List<Invoice> findAllByInvoiceType(InvoiceType invoiceType);
    Invoice findInvoiceByInvoiceNo(String invoiceNumber);

    List<Invoice> findTop3ByAndCompany_TitleAndStatusOrderByDateDesc( String companyTitle, InvoiceStatus status);


    boolean existsByClientVendor_Id(Long clientVendorId);

}
