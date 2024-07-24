package com.cydeo.repository;

import com.cydeo.entity.Invoice;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

public interface InvoiceRepository {

    List<Invoice> findAll();
    Invoice findById(Long id);
    Invoice save(Invoice invoice);
    void deleteById(Invoice invoice);
}
