package com.cydeo.service.s_impl;

import com.cydeo.dto.ClientVendorDto;
import com.cydeo.dto.ProductDto;
import com.cydeo.entity.ClientVendor;
import com.cydeo.entity.Product;
import com.cydeo.entity.User;
import com.cydeo.exception.ProductNotFoundException;
import com.cydeo.mapper.ProductMapper;
import com.cydeo.repository.ProductRepository;
import com.cydeo.repository.UserRepository;
import com.cydeo.service.*;
import com.cydeo.util.MapperUtil;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final MapperUtil mapperUtil;
    private final InvoiceProductService invoiceProductService;
    private final SecurityService securityService;


    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper, MapperUtil mapperUtil, @Lazy InvoiceService invoiceService, InvoiceProductService invoiceProductService, SecurityService securityService) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.mapperUtil = mapperUtil;
        this.invoiceProductService = invoiceProductService;
        this.securityService = securityService;
    }


    public List<ProductDto> listAllProducts() {
        Long companyId = securityService.getLoggedInCompanyId();
        List<Product> productList = productRepository.findAllByCompanyIdAndIsDeleted(companyId,false);
        return productList.stream()
                .map(product -> mapperUtil.convert(product,new ProductDto()))
                .collect(Collectors.toList());
    }

    @Override
    public ProductDto getProductById(Long id) {
        Product product = productRepository.findById(id).orElseThrow();
        return productMapper.convertToDto(product);
    }

    @Override

    public void save(ProductDto productDto) {
        Product product = mapperUtil.convert(productDto, new Product());
        productRepository.save(product);
    }

@Override
public void delete(Long id) {
    Product product=productRepository.findByIdAndIsDeleted(id,false).orElseThrow(
            () -> new ProductNotFoundException("Product cannot be found with ID "+id)
    );
    if (product.getQuantityInStock() == 0 && !invoiceProductService.existsByProductIdAndIsDeleted(id,false)){
        product.setIsDeleted(true);
        productRepository.save(product);
    }}

    @Override
    public void increaseProductQuantityInStock(Long id, Integer quantity) {
        Product product=productRepository.findById(id)
                .orElseThrow(()->new ProductNotFoundException("Product not found with id: " + id));
        int newQuantity=quantity + product.getQuantityInStock();
        product.setQuantityInStock(newQuantity);

        productRepository.save(product);
    }


}