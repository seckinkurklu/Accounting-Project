package com.cydeo.dto;

import com.cydeo.enums.InvoiceStatus;
import com.cydeo.enums.InvoiceType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceDto {

private Long id;

private String invoiceNo;

private InvoiceStatus invoiceStatus;

private InvoiceType invoiceType;

@DateTimeFormat(pattern = "yyyy-MM-dd")
private LocalDate date;

private CompanyDTO company;

@NotNull
private  ClientVendorDto clientVendorDto;

private BigDecimal price;

private BigDecimal tax;

private BigDecimal total;


}
