package com.cydeo.entity;

import com.cydeo.entity.common.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "app_user")
public class User extends BaseEntity {
}
