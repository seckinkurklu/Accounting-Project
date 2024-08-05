package com.cydeo.dto;

import com.cydeo.enums.ProductUnit;
import com.cydeo.exception.ProductLowLimitAlertException;
import com.cydeo.exception.ProductNotFoundException;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductDto {
    private Long id;

    @NotBlank(message = "Product Name is required field.")
    @Size(min = 2,max = 100,message = "Product Name must be between 2 and 100 characters long.")
    private String name;

    private Integer quantityInStock;

    @NotNull(message = "Low Limit Alert is a required field.")
    @Min(value = 2,message = "Low Limit Alert should be at least 1.")
    private Integer lowLimitAlert;

    @NotNull(message = "Product Unit is a required field.")
    private ProductUnit productUnit;
    private CategoryDto category;
    private boolean hasInvoiceProduct;

    public @NotNull(message = "Low Limit Alert is a required field.") @Min(value = 2, message = "Low Limit Alert should be at least 1.") Integer getLowLimitAlert() {
        return lowLimitAlert;
    }
    public Integer getQuantityInStock() {
        return quantityInStock;
    }

    public void setQuantityInStock(Integer quantityInStock) {
        this.quantityInStock = quantityInStock;

    }

    public void setLowLimitAlert(@NotNull(message = "Low Limit Alert is a required field.") @Min(value = 2, message = "Low Limit Alert should be at least 1.") Integer lowLimitAlert) {
        this.lowLimitAlert = lowLimitAlert;

    }


}
