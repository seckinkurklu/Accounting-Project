package com.cydeo.entity;

import com.cydeo.entity.common.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "addresses")
public class Address extends BaseEntity {

    private String  address1;
    private String address2;
    private String city;
    private String state;
    private String country;
    private String zipCode;

}
