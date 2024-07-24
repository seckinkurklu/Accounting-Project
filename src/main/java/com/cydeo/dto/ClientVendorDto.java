package com.cydeo.dto;

import com.cydeo.enums.ClientVendorType;
import lombok.*;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ClientVendorDto {
    private Long id;
    private String clientVendorName;
    private String phone;
    private String website;
    private ClientVendorType clientVendorType;
    private AddressDto address;
    private CompanyDto company;
    boolean hasInvoice;
}
