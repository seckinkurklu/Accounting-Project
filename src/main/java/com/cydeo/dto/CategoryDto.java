package com.cydeo.dto;

import lombok.*;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CategoryDto {
    private Long id;
    private String description;
    private CompanyDto company;
    private boolean hasProduct;
    private ProductDto product;
}
