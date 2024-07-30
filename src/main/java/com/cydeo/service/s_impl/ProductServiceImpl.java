package com.cydeo.service.s_impl;

import com.cydeo.dto.ProductDto;
import com.cydeo.entity.Product;
import com.cydeo.mapper.ProductMapper;
import com.cydeo.repository.ProductRepository;
import com.cydeo.service.ProductService;
import com.cydeo.util.MapperUtil;
import org.springframework.stereotype.Service;

import java.util.List;

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
                .map(product -> mapperUtil.convert(product, new ProductDto())).toList();
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
        Product product = productRepository.findByIdAndIsDeleted(id, false);
        product.setIsDeleted(true);
        productRepository.save(product);


    }

    @Override
    public void increaseProductQuantityInStock(Long id, Integer quantity) {
        Product product=productRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Product not found with id: " + id));
        int newQuantity=quantity + product.getQuantityInStock();
        product.setQuantityInStock(newQuantity);

        productRepository.save(product);
    }
}