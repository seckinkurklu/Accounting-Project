package com.cydeo.service.impl;

import com.cydeo.dto.UserDto;
import com.cydeo.entity.User;
import com.cydeo.mapper.UserMapper;
import com.cydeo.repository.UserRepository;
import com.cydeo.service.UserService;
import com.cydeo.util.MapperUtil;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final MapperUtil mapperUtil;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, MapperUtil mapperUtil, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.mapperUtil = mapperUtil;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public UserDto findByUsername(String username) {
        User user = userRepository.findByUsername(username);
        return userMapper.convertToDto(user);
    }

    @Override
    public List<UserDto> listAllUsers() {

        List<User> userList = userRepository.findAll();

        return userList.stream().map(user -> mapperUtil.convert(user, new UserDto())).collect(Collectors.toList());
    }

    @Override
    public UserDto getLoggedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return mapperUtil.convert(userRepository.findByUsername(username), new UserDto());
    }

    @Override
    public boolean findByUsernameCheck(String userName) {
        return false;
    }

    @Override
    public UserDto findById(Long id) {
        return null;
    }

    @Override
    public void save(UserDto userDto) {
        userDto.setEnabled(true);
        User user = mapperUtil.convert(userDto, new User());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public UserDto updateUser(UserDto userDtoToBeUpdate) {

        User existingUser = userRepository.findById(userDtoToBeUpdate.getId())
                .orElseThrow(() -> new RuntimeException("User can't found with id " + userDtoToBeUpdate.getId()));
        existingUser.setUsername(userDtoToBeUpdate.getUsername());
        existingUser.setPassword(passwordEncoder.encode(userDtoToBeUpdate.getPassword()));
        userRepository.save(existingUser);
        return mapperUtil.convert(existingUser, new UserDto());
    }

    @Override
    public void deleteUser(Long id) {

        User user = userRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        user.setIsDeleted(true);
        userRepository.save(user);


    }

}
