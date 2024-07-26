package com.cydeo.service;

import com.cydeo.dto.UserDto;
import com.cydeo.entity.User;

import java.util.List;

public interface UserService {

    UserDto findByUsername(String username);
    List<UserDto> listAllUsers();
    UserDto getLoggedUser();



//    List<UserDto> findAllAdminsSorted();

    List<UserDto> findAllByRole();



}
