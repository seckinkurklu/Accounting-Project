package com.cydeo.service.impl;

import com.cydeo.dto.InvoiceProductDto;
import com.cydeo.dto.UserDto;
import com.cydeo.entity.Company;
import com.cydeo.entity.InvoiceProduct;
import com.cydeo.entity.User;
import com.cydeo.repository.InvoiceProductRepository;
import com.cydeo.service.InvoiceProductService;
import com.cydeo.service.SecurityService;
import com.cydeo.util.MapperUtil;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class InvoiceProductServiceImpl implements InvoiceProductService {

    private final InvoiceProductRepository invoiceProductRepository;
    private final MapperUtil mapperUtil;
    private final SecurityService securityService;

    public InvoiceProductServiceImpl(InvoiceProductRepository invoiceProductRepository, MapperUtil mapperUtil, SecurityService securityService) {
        this.invoiceProductRepository = invoiceProductRepository;
        this.mapperUtil = mapperUtil;
        this.securityService = securityService;
    }
  
    public List<InvoiceProductDto> getAllInvoiceProducts() {
        // Mevcut kullanıcıyı al
//         UserDto loggedUser = securityService.getLoggedInUser();
//         User user = mapperUtil.convert(loggedUser,new User());
//         Company companyTitle = user.getCompany();
      //Update edilecek - Omer

        // Kullanıcıya ait InvoiceProduct nesnelerini al
//        List<InvoiceProduct> invoiceProducts = invoiceProductRepository.findAllByInvoiceCompany(companyTitle);
        List<InvoiceProduct> invoiceProducts = invoiceProductRepository.findAll();

        // InvoiceProduct nesnelerini InvoiceProductDto'ya dönüştür
        List<InvoiceProductDto> invoiceProductDtos = invoiceProducts.stream()
                .map(invoiceProduct -> mapperUtil.convert(invoiceProduct, new InvoiceProductDto()))
                .toList();

        // Toplam ve vergi hesaplamalarını yap
        invoiceProductDtos = invoiceProductDtos.stream().map(invoiceProductDto -> {
            Long id = invoiceProductDto.getId();
            if (id == null) {
                return invoiceProductDto;
            }

            InvoiceProduct invoiceProduct = invoiceProductRepository.findById(id).orElse(null);
            if (invoiceProduct == null) {
                return invoiceProductDto;
            }

            int quantity = invoiceProduct.getQuantity();
            BigDecimal price = invoiceProduct.getPrice() != null ? invoiceProduct.getPrice() : BigDecimal.ZERO;
            BigDecimal tax = invoiceProduct.getTax() != null ? invoiceProduct.getTax() : BigDecimal.ZERO;

            // Toplam fiyatı hesapla
            BigDecimal basePrice = price.multiply(BigDecimal.valueOf(quantity));
            invoiceProductDto.setTotal(basePrice);

            BigDecimal taxRate = invoiceProduct.getTax();
            invoiceProductDto.setTax(taxRate);

            // Toplam vergiyi hesapla
            BigDecimal totalTax = basePrice.multiply(taxRate).divide(BigDecimal.valueOf(100));
            invoiceProductDto.setTotal(totalTax);

            // Toplam fiyat ve vergi dahil toplamı hesapla
            invoiceProductDto.setTotal(basePrice.add(totalTax));
            return invoiceProductDto;
        }).toList();

        return invoiceProductDtos;

    }

@Override
public void createInvoiceProduct(InvoiceProductDto invoiceProductDto) {. // hep birlikteyken ismi degisrilebilir. 
    // InvoiceProductDto'yu InvoiceProduct'a dönüştür
    InvoiceProduct newInvoiceProduct = mapperUtil.convert(invoiceProductDto, new InvoiceProduct());

    // Mevcut InvoiceProduct'ları al
    List<InvoiceProduct> invoiceProducts = invoiceProductRepository.findAll();

    // Aynı üründen olup olmadığını kontrol et
    for (InvoiceProduct existingProduct : invoiceProducts) {
        if (existingProduct.getProduct().getId().equals(newInvoiceProduct.getProduct().getId()) &&
                existingProduct.getInvoice().getId().equals(newInvoiceProduct.getInvoice().getId())) {
            // Mevcut ürün bulundu, miktarını ve fiyatını güncelle

            // Null kontrolleri
            Integer existingQuantity = existingProduct.getQuantity() != 0 ? existingProduct.getQuantity() : 0;
            Integer newQuantity = newInvoiceProduct.getQuantity() != 0 ? newInvoiceProduct.getQuantity() :0;
            BigDecimal existingPrice = existingProduct.getPrice() != null ? existingProduct.getPrice() : BigDecimal.ZERO;
            BigDecimal newPrice = newInvoiceProduct.getPrice() != null ? newInvoiceProduct.getPrice() : BigDecimal.ZERO;
            BigDecimal existingTax = existingProduct.getTax() != BigDecimal.ZERO ? existingProduct.getTax() : BigDecimal.ZERO;
            BigDecimal newTax = newInvoiceProduct.getTax() != BigDecimal.ZERO ? newInvoiceProduct.getTax() : BigDecimal.ZERO;
            BigDecimal existingTotal = existingProduct.getTotal() != null ? existingProduct.getTotal() : BigDecimal.ZERO;
            BigDecimal newTotal = newInvoiceProduct.getTotal() != null ? newInvoiceProduct.getTotal() : BigDecimal.ZERO;

            existingProduct.setQuantity(existingQuantity + newQuantity);
            existingProduct.setPrice(existingPrice.add(newPrice));
            existingProduct.setTax(existingTax.add(newTax));
            existingProduct.setTotal(existingTotal.add(newTotal));
            invoiceProductRepository.save(existingProduct);
            return;
        }
      invoiceProductRepository.save(newInvoiceProduct);
    }
      
    @Override
    public InvoiceProductDto getInvoiceProductById(Long id) {
        return mapperUtil.convert(invoiceProductRepository.findById(id).orElse(null), new InvoiceProductDto());
    }
          public List<InvoiceProductDto> getAllInvoiceProductsById(Long id) {
        return invoiceProductRepository.findAllInvoiceProductsByInvoiceId(id).stream().map(p->mapperUtil.convert(p, new InvoiceProductDto())).toList();
    }

    @Override
    public InvoiceProductDto updateInvoiceProduct(Long id, InvoiceProduct invoiceProduct) {
        return null;
    }

    @Override
    public void deleteInvoiceProduct(Long id) {
       
    }

    @Override
    public void save(InvoiceProductDto invoiceProductDTO) {

    }
}