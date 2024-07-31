package com.cydeo.dto;

import com.cydeo.enums.ClientVendorType;

import lombok.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class ClientVendorDto {
    private Long id;

    @NotBlank(message = "{client.vendor.name.required}")
    @Size(min = 2, max = 50, message = "{client.vendor.name.length}")
    private String clientVendorName;

    @NotBlank(message = "{client.vendor.phone.required}")
    @Pattern(regexp = "^[+]?[0-9\\-\\s]+$", message = "{client.vendor.phone.required}")
    private String phone;

    @Pattern(regexp = "^http(s{0,1})://[a-zA-Z0-9/\\-\\.]+.([A-Za-z/]{2,5})[a-zA-Z0-9/\\&\\?\\=\\-\\.\\~\\%]*$", message = "{client.vendor.website.format}")
    private String website;

    @NotNull(message = "{client.vendor.type.required}")
    private ClientVendorType clientVendorType;

    @NotNull(message = "{client.vendor.address.required}")
    private AddressDto address;

    @NotNull(message = "{client.vendor.company.required}")
    private CompanyDto company;
    private boolean hasInvoice;

}
