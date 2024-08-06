package com.cydeo.service;

import com.cydeo.dto.RoleDto;
import com.cydeo.dto.UserDto;
import com.cydeo.entity.User;
import com.cydeo.exception.UserNotFoundException;

import java.util.List;

public interface UserService {

    UserDto findByUsername(String username);

    List<UserDto> listAllUsers();

    UserDto getLoggedUser();


    // List<UserDto> findAllAdminsSorted();
//  List<UserDto> findAllByRole();
    boolean findByUsernameCheck(String userName);

    UserDto findById(Long id) throws UserNotFoundException;

    void save(UserDto user);

    UserDto updateUser(UserDto dto) throws UserNotFoundException;

    void deleteUser(Long id) throws UserNotFoundException;

    UserDto getCurrentUser();
}
