package com.cydeo.service;

import com.cydeo.dto.ProductDto;

import java.util.List;

public interface ProductService {
    List<ProductDto> listAllProducts();
    ProductDto getProductById(Long id);

    void save(ProductDto productDto);

    void delete(Long id);

    void increaseProductQuantityInStock(Long id, Integer quantity);

}
