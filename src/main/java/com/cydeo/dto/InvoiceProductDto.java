package com.cydeo.dto;

import lombok.*;

import java.math.BigDecimal;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class InvoiceProductDto {
    private Long id;
    private Integer quantity;
    private BigDecimal price;
    private BigDecimal tax;
    private BigDecimal total;
    private BigDecimal profitLoss;
    private Integer remainingQuantity;
    private InvoiceDto invoice;
    private ProductDto product;

}
