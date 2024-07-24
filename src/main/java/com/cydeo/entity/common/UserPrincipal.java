package com.cydeo.entity.common;

import com.cydeo.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

// to convert my user entity to spring security standard (UserDetails interface)

public class UserPrincipal implements UserDetails {
    // like as mapper,
    // get to User from db or any user that you want to,
    // and convert to UserDetails Spring understands only this (UserDetails)

    private User user; // I am giving one User and convert to userDetails

    public UserPrincipal(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorityList = new ArrayList<>();
        GrantedAuthority authority = new SimpleGrantedAuthority(this.user.getRole().getDescription());
        authorityList.add(authority);
        return authorityList;
    }
    //Add getId method to be able to use it in BaseEntityListener class
    public Long getId(){
        return this.user.getId();
    }

    //    Add getFullNameForProfile method to show logged-in user firstname and lastname in UI dropdown menu
    public String getFullNameForProfile() {
        String fullName = this.user.getFirstname()+" "+ this.user.getLastname();
        return fullName;
    }

    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    @Override
    public String getUsername() {
        return this.user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.user.isEnabled();
    }


}
