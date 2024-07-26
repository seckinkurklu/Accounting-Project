package com.cydeo.converter;

import com.cydeo.dto.InvoiceDto;
import com.cydeo.entity.Invoice;
import com.cydeo.repository.InvoiceRepository;

public class InvoiceDTOConverter {
    public InvoiceDto convert(Invoice invoice){
        return new InvoiceDto();

    }
}
