package com.cydeo.entity;

import com.cydeo.entity.common.BaseEntity;
import com.cydeo.enums.InvoiceStatus;
import com.cydeo.enums.InvoiceType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "invoices")
public class Invoice extends BaseEntity {

    private String invoiceNo;
    @Enumerated(EnumType.STRING)
    private InvoiceStatus invoiceStatus;
    @Enumerated(EnumType.STRING)
    private InvoiceType invoiceType;

    private LocalDate date;
    @ManyToOne
    @JoinColumn (name = "client_vendor_id")
    private ClientVendor clientVendor;//will be seen under "client_vendor_id" column on the "invoices" table
    @ManyToOne
    @JoinColumn (name = "company_id")
    private Company company; // will be seen under "company_id" column on the "invoices" table
}
