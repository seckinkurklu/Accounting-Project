package com.cydeo.service.impl;

import com.cydeo.dto.UserDto;
import com.cydeo.entity.User;
import com.cydeo.exception.UserNotFoundException;
import com.cydeo.mapper.UserMapper;
import com.cydeo.repository.UserRepository;
import com.cydeo.service.UserService;
import com.cydeo.util.MapperUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
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

        return userList.stream().map(user -> mapperUtil.convert(user, UserDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<UserDto> findAllByRole() {

        List<User> userList = userRepository.findAllByRole_Description("Admin");


        return userList.stream().map(user -> mapperUtil.convert(user, UserDto.class)).collect(Collectors.toList());
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
        User user = mapperUtil.convert(userDto, User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public UserDto updateUser(UserDto userDtoToBeUpdate) {

        User existingUser = userRepository.findById(userDtoToBeUpdate.getId())
                .orElseThrow(() -> new UserNotFoundException("User can't found with id " + userDtoToBeUpdate.getId()));
        existingUser.setUsername(userDtoToBeUpdate.getUsername());
        existingUser.setPassword(passwordEncoder.encode(userDtoToBeUpdate.getPassword()));
        userRepository.save(existingUser);
        return mapperUtil.convert(existingUser, UserDto.class);
    }
}
