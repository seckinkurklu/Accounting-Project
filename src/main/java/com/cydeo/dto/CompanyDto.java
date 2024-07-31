package com.cydeo.dto;

import com.cydeo.enums.CompanyStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDto {

    private Long id;
    @Column(unique = true)
    @NotBlank(message = "Title is a required field.")
    @Size(min = 2, max = 100, message = "Title should be 2-100 characters long.")
    private String title;

    @NotBlank(message = "Phone Number is required field and may be in any valid phone number format.")
    @Pattern(regexp = "\\+?\\d{1,4}?[-.\\s]?\\(?(\\d{1,3})\\)?[-.\\s]?\\d{1,4}[-.\\s]?\\d{1,4}[-.\\s]?\\d{1,9}",
            message = "Phone Number is required field and may be in any valid phone number format.")
    private String phone;

    @Pattern(regexp = "^https:\\/\\/www\\.[a-zA-Z0-9-]+\\.com\\/?$", message = "Website should have a valid format.")
    private String website;

    @Valid
    private AddressDto address;
    private CompanyStatus companyStatus;

}
