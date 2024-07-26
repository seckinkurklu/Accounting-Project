package com.cydeo.service;

import com.cydeo.dto.RoleDto;
import com.cydeo.dto.UserDto;
import com.cydeo.entity.User;

import java.util.List;

public interface UserService {

    UserDto findByUsername(String username);
    List<UserDto> listAllUsers();
    UserDto getLoggedUser();



// List<UserDto> findAllAdminsSorted();
//  List<UserDto> findAllByRole();
    boolean findByUsernameCheck(String userName);
    UserDto findById(Long id);
void save(UserDto user);
UserDto updateUser(UserDto dto);
}
