package com.cydeo.entity;

import com.cydeo.entity.common.BaseEntity;
import com.cydeo.enums.ClientVendorType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "clients_vendors")
public class ClientVendor extends BaseEntity {
    private String clientVendorName;
    private String phone;
    private String website;
    @Enumerated(EnumType.STRING)
    private ClientVendorType clientVendorType;
    @OneToOne
    @JoinColumn(name = "address_id")
    Address address; //will be seen under "address_id" column on the "clients_vendors" table
    @ManyToOne
    @JoinColumn(name = "company_id")
    Company company; // will be seen under "company_id" column on the "clients_vendors" table
}
