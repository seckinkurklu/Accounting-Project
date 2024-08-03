package com.cydeo.service.s_impl;

import com.cydeo.dto.UserDto;
import com.cydeo.entity.User;
import com.cydeo.exception.UserNotFoundException;
import com.cydeo.mapper.UserMapper;
import com.cydeo.repository.UserRepository;
import com.cydeo.service.SecurityService;
import com.cydeo.service.UserService;
import com.cydeo.util.MapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
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
    private final SecurityService securityService;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, MapperUtil mapperUtil, PasswordEncoder passwordEncoder, @Lazy SecurityService securityService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.mapperUtil = mapperUtil;
        this.passwordEncoder = passwordEncoder;
        this.securityService = securityService;
    }
    //there was SecurityServiceImpl, we should use Service part


    @Override
    public UserDto findByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(user -> mapperUtil.convert(user, new UserDto()))
                .orElseThrow(() -> new UserNotFoundException("User can't found with " +username));
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
       // return false; // it is wrong
        return userRepository.findByUsername(userName).isPresent(); // it was above, and it was wrong, changed with this one
    }

    @Override
    public UserDto findById(Long id) {
        return userRepository.findById(id)
                .map(user -> mapperUtil.convert(user, new UserDto()))
                .orElseThrow(() -> new UserNotFoundException("User can't found with id " + id));
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
                .orElseThrow(() -> new UserNotFoundException("User can't found with id " + userDtoToBeUpdate.getId()));
        existingUser.setUsername(userDtoToBeUpdate.getUsername());
        existingUser.setPassword(passwordEncoder.encode(userDtoToBeUpdate.getPassword()));
        userRepository.save(existingUser);
        return mapperUtil.convert(existingUser, new UserDto());
    }

    @Override
    public void deleteUser(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
        user.setIsDeleted(true);
        userRepository.save(user);

    }

    @Override
    public UserDto getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new UserNotFoundException("User not found with " + username));
        return mapperUtil.convert(user,new UserDto());

    }

}
