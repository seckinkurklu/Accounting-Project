package com.cydeo.service.impl;

import com.cydeo.dto.InvoiceProductDto;
import com.cydeo.entity.InvoiceProduct;
import com.cydeo.mapper.MapperUtil;
import com.cydeo.repository.InvoiceProductRepository;
import com.cydeo.service.InvoiceProductService;

import java.util.List;


public class InvoiceProductServiceImpl implements InvoiceProductService {

    private final InvoiceProductRepository invoiceProductRepository;
    private final MapperUtil mapperUtil;


    public InvoiceProductServiceImpl(InvoiceProductRepository invoiceProductRepository, MapperUtil mapperUtil) {
        this.invoiceProductRepository = invoiceProductRepository;
        this.mapperUtil = mapperUtil;
    }


    @Override
    public List<InvoiceProduct> getAllInvoiceProducts() {
        return getAllInvoiceProducts();
    }

    @Override
    public InvoiceProduct getInvoiceProductById(Long id) {
        return getInvoiceProductById(id);
    }

    @Override
    public InvoiceProduct createInvoiceProduct(InvoiceProduct invoiceProduct) {
        return createInvoiceProduct(invoiceProduct);
    }

    @Override
    public InvoiceProduct updateInvoiceProduct(Long id, InvoiceProduct invoiceProduct) {
        return null;
    }

    @Override
    public void deleteInvoiceProduct(Long id) {
        deleteInvoiceProduct(id);

    }

    @Override
    public void save(InvoiceProductDto invoiceProductDto) {
        invoiceProductRepository.save(mapperUtil.convert(invoiceProductDto,new InvoiceProduct()));
    }
}
