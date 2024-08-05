package com.cydeo.service.s_impl;


import com.cydeo.dto.InvoiceDto;
import com.cydeo.dto.InvoiceProductDto;
import com.cydeo.dto.ProductDto;
import com.cydeo.entity.Invoice;
import com.cydeo.entity.InvoiceProduct;
import com.cydeo.entity.Product;
import com.cydeo.exception.ProductLowLimitAlertException;
import com.cydeo.exception.ProductNotFoundException;

import com.cydeo.dto.CompanyDto;

import com.cydeo.entity.Company;
import com.cydeo.entity.InvoiceProduct;

import com.cydeo.enums.InvoiceStatus;

import com.cydeo.exception.InvoiceProductNotFoundException;


import com.cydeo.repository.InvoiceProductRepository;
import com.cydeo.repository.InvoiceRepository;

import com.cydeo.repository.ProductRepository;

import com.cydeo.service.InvoiceProductService;
import com.cydeo.service.SecurityService;
import com.cydeo.util.MapperUtil;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class InvoiceProductServiceImpl implements InvoiceProductService {

    private final InvoiceProductRepository invoiceProductRepository;
    private final MapperUtil mapperUtil;
    private final SecurityService securityService;

    private final InvoiceRepository invoiceRepository;

    public InvoiceProductServiceImpl(InvoiceProductRepository invoiceProductRepository, MapperUtil mapperUtil, SecurityService securityService, InvoiceRepository invoiceRepository) {
        this.invoiceProductRepository = invoiceProductRepository;
        this.mapperUtil = mapperUtil;
        this.securityService = securityService;
        this.invoiceRepository = invoiceRepository;

    private final ProductRepository productRepository;


    public InvoiceProductServiceImpl(InvoiceProductRepository invoiceProductRepository, MapperUtil mapperUtil, SecurityService securityService, ProductRepository productRepository, InvoiceRepository invoiceRepository) {
        this.invoiceProductRepository = invoiceProductRepository;
        this.mapperUtil = mapperUtil;
        this.securityService = securityService;
        this.productRepository = productRepository;

    }

//    public List<InvoiceProductDto> getAllInvoiceProducts() {
//        // Mevcut kullanıcıyı al
////         UserDto loggedUser = securityService.getLoggedInUser();
////         User user = mapperUtil.convert(loggedUser,new User());
////         Company companyTitle = user.getCompany();
//        //Update edilecek - Omer
//
//        // Kullanıcıya ait InvoiceProduct nesnelerini al
////        List<InvoiceProduct> invoiceProducts = invoiceProductRepository.findAllByInvoiceCompany(companyTitle);
//        List<InvoiceProduct> invoiceProducts = invoiceProductRepository.findAllByIsDeletedFalse();
//
//        // InvoiceProduct nesnelerini InvoiceProductDto'ya dönüştür
//        List<InvoiceProductDto> invoiceProductDtos = invoiceProducts.stream()
//                .map(invoiceProduct -> mapperUtil.convert(invoiceProduct, new InvoiceProductDto()))
//                .toList();
//
//        // Toplam ve vergi hesaplamalarını yap
//        invoiceProductDtos = invoiceProductDtos.stream().map(invoiceProductDto -> {
//            Long id = invoiceProductDto.getId();
//            if (id == null) {
//                return invoiceProductDto;
//            }
//
//            InvoiceProduct invoiceProduct = invoiceProductRepository.findById(id).orElse(null);
//            if (invoiceProduct == null) {
//                return invoiceProductDto;
//            }
//
//
//
//            int quantity = invoiceProduct.getQuantity();
//            BigDecimal price = invoiceProduct.getPrice() != null ? invoiceProduct.getPrice() : BigDecimal.ZERO;
//            BigDecimal tax = invoiceProduct.getTax() != null ? invoiceProduct.getTax() : BigDecimal.ZERO;
//
//            // Toplam fiyatı hesapla
//
//            invoiceProductDto.setTax(tax);
//            invoiceProductDto.setQuantity(quantity);
//            invoiceProductDto.setPrice(price);
//
//            BigDecimal totalPriceWithTax = (price.multiply(BigDecimal.valueOf(quantity))).add((price.multiply(BigDecimal.valueOf(quantity))).multiply(tax)).divide(BigDecimal.valueOf(100));
//            invoiceProductDto.setTotal(totalPriceWithTax);
//
////            // Toplam vergiyi hesapla
////            BigDecimal totalTax = basePrice.multiply(taxRate).divide(BigDecimal.valueOf(100));
////            invoiceProductDto.setTotal(totalTax);
////
////            // Toplam fiyat ve vergi dahil toplamı hesapla
////            invoiceProductDto.setTotal(basePrice.add(totalTax));
//            return invoiceProductDto;
//        }).toList();
//
//        return invoiceProductDtos;
//
//    }

    public List<InvoiceProductDto> getAllInvoiceProducts() {
        // Kullanıcıya ait InvoiceProduct nesnelerini al
        List<InvoiceProduct> invoiceProducts = invoiceProductRepository.findAllByIsDeletedFalse();

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
            BigDecimal taxRate = invoiceProduct.getTax() != null ? invoiceProduct.getTax() : BigDecimal.ZERO;

            // Toplam fiyatı hesapla
            BigDecimal totalPrice = price.multiply(BigDecimal.valueOf(quantity));
            BigDecimal totalTax = totalPrice.multiply(taxRate).divide(BigDecimal.valueOf(100));
            BigDecimal totalPriceWithTax = totalPrice.add(totalTax);

            invoiceProductDto.setTax(taxRate);
            invoiceProductDto.setQuantity(quantity);
            invoiceProductDto.setPrice(price);
            invoiceProductDto.setTotal(totalPriceWithTax);

            return invoiceProductDto;
        }).toList();

        return invoiceProductDtos;
    }
    @Override
    public void save(InvoiceProductDto invoiceProductDto) {
        // InvoiceProductDto'yu InvoiceProduct'a dönüştür
        InvoiceProduct newInvoiceProduct = mapperUtil.convert(invoiceProductDto, new InvoiceProduct());

        // Mevcut InvoiceProduct'ları al
        List<InvoiceProduct> invoiceProducts = invoiceProductRepository.findAll();

        // Aynı üründen olup olmadığını kontrol et
        boolean isUpdated = false;
        for (InvoiceProduct existingProduct : invoiceProducts) {
            if (existingProduct.getProduct().getId().equals(newInvoiceProduct.getProduct().getId()) &&
                    existingProduct.getInvoice().getId().equals(newInvoiceProduct.getInvoice().getId())) {
                // Mevcut ürün bulundu, miktarını ve fiyatını güncelle

                // Null kontrolleri
                Integer existingQuantity = existingProduct.getQuantity() != 0 ? existingProduct.getQuantity() : 0;
                Integer newQuantity = newInvoiceProduct.getQuantity() != 0 ? newInvoiceProduct.getQuantity() : 0;
                BigDecimal existingPrice = existingProduct.getPrice() != null ? existingProduct.getPrice() : BigDecimal.ZERO;
                BigDecimal newPrice = newInvoiceProduct.getPrice() != null ? newInvoiceProduct.getPrice() : BigDecimal.ZERO;
                BigDecimal existingTax = existingProduct.getTax() != null ? existingProduct.getTax() : BigDecimal.ZERO;
                BigDecimal newTax = newInvoiceProduct.getTax() != null ? newInvoiceProduct.getTax() : BigDecimal.ZERO;
                BigDecimal existingTotal = existingProduct.getTotal() != null ? existingProduct.getTotal() : BigDecimal.ZERO;

                // Miktarları ve fiyatları güncelle
                existingProduct.setQuantity(existingQuantity + newQuantity);
                existingProduct.setPrice(newPrice);
                existingProduct.setTax(existingTax);

                // Toplam fiyatı ve vergiyi hesapla
                BigDecimal totalPrice = newPrice.multiply(BigDecimal.valueOf(existingProduct.getQuantity()));
                BigDecimal totalTax = totalPrice.multiply(existingTax).divide(BigDecimal.valueOf(100));
                BigDecimal totalPriceWithTax = totalPrice.add(totalTax);

                existingProduct.setTotal(totalPriceWithTax);
                invoiceProductRepository.save(existingProduct);
                isUpdated = true;
                break;
            }
        }

        // Eğer ürün güncellenmediyse yeni ürünü ekle
        if (!isUpdated) {
            BigDecimal totalPrice = newInvoiceProduct.getPrice().multiply(BigDecimal.valueOf(newInvoiceProduct.getQuantity()));
            BigDecimal totalTax = totalPrice.multiply(newInvoiceProduct.getTax()).divide(BigDecimal.valueOf(100));
            BigDecimal totalPriceWithTax = totalPrice.add(totalTax);
            newInvoiceProduct.setTotal(totalPriceWithTax);
            invoiceProductRepository.save(newInvoiceProduct);
        }
    }


    @Override
    public List<InvoiceProductDto> getAllInvoiceProductsById(Long id) {
        return invoiceProductRepository.findAllInvoiceProductsByInvoiceIdAndIsDeletedFalse(id).stream().map(p -> mapperUtil.convert(p, new InvoiceProductDto())).toList();

    }

    @Override
    public void delete(Long id) {
        InvoiceProduct invoiceProduct = invoiceProductRepository.findById(id).orElseThrow(() ->new InvoiceProductNotFoundException("Invoice product not found with id: "+id));
        if (invoiceProduct != null) {
            invoiceProduct.setIsDeleted(true);
            invoiceProductRepository.save(invoiceProduct);
        }
    }

    @Override
    public boolean existsByProductIdAndIsDeleted(Long id, boolean isDeleted) {
        return invoiceProductRepository.existsByProductIdAndIsDeleted(id,isDeleted);
    }

    @Override

    public void deleteByInvoiceId(Long invoiceId) {
        List<InvoiceProduct> invoiceProducts = invoiceProductRepository.findAllInvoiceProductsByInvoiceIdAndIsDeletedFalse(invoiceId);
        for (InvoiceProduct invoiceProduct : invoiceProducts) {
            delete(invoiceProduct.getId());
        }


    public void checkForLowQuantityAlert(Long id) {

        InvoiceProduct invoiceProduct = invoiceProductRepository.findById(id).orElse(null);
        if (invoiceProduct == null) {
            throw new IllegalArgumentException("Invoice product not found for ID: " + id);
        }

        int remainingQuantity = invoiceProduct.getRemainingQuantity();
        Product product = invoiceProduct.getProduct();
        if (remainingQuantity < product.getLowLimitAlert()) {

            throw new ProductLowLimitAlertException("Stock of " + product.getName() + " decreased below low limit!");
        }

    }





    public List<InvoiceProductDto> findAllApprovedInvoiceInvoiceProduct(InvoiceStatus invoiceStatus) {
        CompanyDto companyDto=securityService.getLoggedInUser().getCompany();
        Company company=mapperUtil.convert(companyDto,new Company());
        return invoiceProductRepository.findByInvoice_CompanyAndInvoice_InvoiceStatusOrderByInsertDateTime(company,invoiceStatus)
                .stream().map(invoiceProduct -> mapperUtil.convert(invoiceProduct, new InvoiceProductDto())).toList();

    }


    @Override
    public InvoiceProductDto getInvoiceProductById(Long id) {
        return mapperUtil.convert(invoiceProductRepository.findById(id).orElseThrow(() ->new InvoiceProductNotFoundException("Invoice product not found with id: "+id)), new InvoiceProductDto());
    }

//    @Override
//    public boolean existsByInvoiceIdAndIsDeleted(Long id, boolean isDeleted) {
//        return invoiceProductRepository.existsByInvoiceIdAndIsDeleted(id,isDeleted);
//    }
}