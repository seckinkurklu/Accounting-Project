package com.cydeo.service.impl;

import com.cydeo.dto.InvoiceProductDto;
import com.cydeo.entity.InvoiceProduct;
import com.cydeo.repository.InvoiceProductRepository;
import com.cydeo.service.InvoiceProductService;
import com.cydeo.util.MapperUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvoiceProductServiceImpl implements InvoiceProductService {

    private final InvoiceProductRepository invoiceProductRepository;
    private final MapperUtil mapperUtil;

    public InvoiceProductServiceImpl(InvoiceProductRepository invoiceProductRepository, MapperUtil mapperUtil) {
        this.invoiceProductRepository = invoiceProductRepository;
        this.mapperUtil = mapperUtil;
    }


    @Override
    public List<InvoiceProductDto> getAllInvoiceProducts() {
        List<InvoiceProduct> invoiceProducts = invoiceProductRepository.findAll();
        return invoiceProducts.stream().map(invoiceProduct -> mapperUtil.convert(invoiceProduct,new InvoiceProductDto())).toList();
    }

    @Override
    public InvoiceProductDto getInvoiceProductById(Long id) {

        List<InvoiceProduct> invoiceProduct=invoiceProductRepository.findAllByInvoice_Id(id);

        return mapperUtil.convert(invoiceProduct,new  InvoiceProductDto());
    }


    @Override
    public void createInvoiceProduct(InvoiceProductDto invoiceProductDto) {
        InvoiceProduct invoiceProduct = mapperUtil.convert(invoiceProductDto,new InvoiceProduct());
         invoiceProductRepository.save(invoiceProduct);
    }

    @Override
    public InvoiceProduct updateInvoiceProduct(Long id, InvoiceProduct invoiceProduct) {
        return null;
    }

    @Override
    public void deleteInvoiceProduct(Long id) {
        deleteInvoiceProduct(id);

    }
}
