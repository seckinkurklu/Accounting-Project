package com.cydeo.service.s_impl;

import com.cydeo.dto.UserDto;
import com.cydeo.entity.User;
import com.cydeo.entity.common.UserPrincipal;
import com.cydeo.repository.UserRepository;
import com.cydeo.service.SecurityService;
import com.cydeo.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SecurityServiceImpl implements SecurityService {

    private final UserRepository userRepository;
    private final UserService userService;


    public SecurityServiceImpl(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;

        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user =userRepository.findByUsername(username);
        if (user==null) {
            throw new UsernameNotFoundException(username);
        }

        return new UserPrincipal(user);
    }

    @Override
    public UserDto getLoggedInUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userService.findByUsername(username);
    }
    public  Long getLoggedInCompanyId(){
       return getLoggedInUser().getCompany().getId();
    };
}