package com.cydeo.dto;

import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CategoryDto {
    private Long id;

    @NotBlank(message = "Description is a required field.")
    @Size(min = 2, max = 100, message = "Description must be between 2 and 100 characters long.")
    @Column(unique = true, nullable = false)
    private String description;

    private CompanyDto company;
    private boolean hasProduct;
    private ProductDto product;
}
