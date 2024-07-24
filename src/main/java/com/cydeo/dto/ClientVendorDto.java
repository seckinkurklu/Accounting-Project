package com.cydeo.dto;

import com.cydeo.enums.ClientVendorType;

import lombok.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class ClientVendorDto {
    private Long id;
    private String clientVendorName;
    private String phone;
    private String website;
    private ClientVendorType clientVendorType;
    private AddressDto address;

    private CompanyDTO company;
    private boolean hasInvoice;

}
