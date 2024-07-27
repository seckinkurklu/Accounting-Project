package com.cydeo.service.impl;

import com.cydeo.converter.InvoiceProductDTOConverter;
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
        return invoiceProducts.stream().map(p->mapperUtil.convert(p, new InvoiceProductDto())).toList() ;
    }

    @Override
    public List<InvoiceProductDto> getAllInvoiceProductsById(Long id) {
        return invoiceProductRepository.findAllInvoiceProductsByInvoiceId(id).stream().map(p->mapperUtil.convert(p, new InvoiceProductDto())).toList();
    }

    @Override
    public InvoiceProductDto getInvoiceProductById(Long id) {
        return mapperUtil.convert(invoiceProductRepository.findById(id).orElse(null), new InvoiceProductDto());
    }

    @Override
    public InvoiceProductDto createInvoiceProduct(InvoiceProduct invoiceProduct) {
        invoiceProductRepository.save(mapperUtil.convert(invoiceProduct, new InvoiceProduct()));
        return createInvoiceProduct(invoiceProduct);
    }

    @Override
    public InvoiceProductDto updateInvoiceProduct(Long id, InvoiceProduct invoiceProduct) {
        return null;
    }

    @Override
    public void deleteInvoiceProduct(Long id) {
        deleteInvoiceProduct(id);

    }
}
