package com.cydeo.dto;

import com.cydeo.enums.Months;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDto {

    private Long id;

    private Integer year;

    private Months month;

    private LocalDate paymentDate;

    private BigDecimal amount;

    private boolean isPaid;

    private String companyStripeId;

    private String description;

    private CompanyDto company;

}
