package com.cydeo.service.Impl;

import com.cydeo.dto.ProductDto;
import com.cydeo.entity.Product;
import com.cydeo.repository.ProductRepository;
import com.cydeo.service.ProductService;
import com.cydeo.util.MapperUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final MapperUtil mapperUtil;

    public ProductServiceImpl(ProductRepository productRepository, MapperUtil mapperUtil) {
        this.productRepository = productRepository;
        this.mapperUtil = mapperUtil;
    }


    @Override
    public List<ProductDto> listAllProducts() {

        List<Product> products = productRepository.findAll();

        return products.stream()
                .map(product -> mapperUtil.convert(product, ProductDto.class)).collect(Collectors.toList());
    }
}
