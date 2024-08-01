package com.cydeo.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CategoryDto {
    private Long id;

    @NotBlank(message = "Category is a required field.")
    private String description;
    private CompanyDto company;
    private boolean hasProduct;
    private ProductDto product;
}
