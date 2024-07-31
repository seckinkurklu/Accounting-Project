package com.cydeo.repository;

import com.cydeo.entity.Company;
import com.cydeo.entity.Invoice;
import com.cydeo.enums.InvoiceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

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

    boolean existsByClientVendor_Id(Long clientVendorId);
}
