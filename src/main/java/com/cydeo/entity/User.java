package com.cydeo.entity;

import com.cydeo.entity.common.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;
import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @Column(unique = true)
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private String phone;
    private boolean enabled;
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role; // will be seen under "role_id" column on the "users" table
    @ManyToOne
    @JoinColumn(name = "company_id")
    Company company; // will be seen under "company_id" column on the "users" table

}
