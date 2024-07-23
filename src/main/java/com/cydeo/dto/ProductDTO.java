package com.cydeo.dto;

import com.cydeo.enums.ProductUnit;

public class ProductDTO {

    private long id;

    private String name;

    private Integer quantityInStocks;

    private Integer lowLimitAlert;

    ProductUnit productUnit;

    CategoryDto category;

    boolean hasProduct;


}
