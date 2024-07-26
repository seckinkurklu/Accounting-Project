package com.cydeo.service.impl;

import com.cydeo.dto.ProductDto;
import com.cydeo.entity.Product;
import com.cydeo.mapper.ProductMapper;
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
    private final ProductMapper productMapper;
    private final MapperUtil mapperUtil;

    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper, MapperUtil mapperUtil) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.mapperUtil = mapperUtil;
    }


    @Override
    public List<ProductDto> listAllProducts() {

        List<Product> products = productRepository.findAll();

        return products.stream()
                .map(product -> mapperUtil.convert(product, new ProductDto())).collect(Collectors.toList());
    }

    @Override
    public ProductDto getProductById(Long id) {

        Product product = productRepository.findById(id).orElseThrow();

        return productMapper.convertToDto(product);
    }
}
