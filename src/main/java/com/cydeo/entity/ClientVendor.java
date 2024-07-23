package com.cydeo.entity;

import com.cydeo.entity.common.BaseEntity;
import com.cydeo.enums.ClientVendorType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "clients_vendors")
@Getter
@Setter
@NoArgsConstructor
public class ClientVendor extends BaseEntity {
    private String clientVendorName;
    private String phone;
    private String website;

    @Enumerated(EnumType.STRING)
    private ClientVendorType clientVendorType;

    @OneToOne
    @JoinColumn(name = "address_id")
    private Address address ;//one-to-one / will be seen under "address_id" column on the "clients_vendors" table

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;
}
