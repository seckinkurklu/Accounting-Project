package com.cydeo.service.Impl;

import com.cydeo.dto.UserDto;
import com.cydeo.entity.User;
import com.cydeo.mapper.UserMapper;
import com.cydeo.repository.UserRepository;
import com.cydeo.service.UserService;
import com.cydeo.util.MapperUtil;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final MapperUtil mapperUtil;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, MapperUtil mapperUtil) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.mapperUtil = mapperUtil;
    }


    @Override
    public UserDto findByUsername(String username) {
        User user=userRepository.findByUsername(username);
        return userMapper.convertToDto(user);
    }

    @Override
    public List<UserDto> listAllUsers() {

        List<User> userList=userRepository.findAll();

        return userList.stream().map(user -> mapperUtil.convert(user, UserDto.class)).collect(Collectors.toUnmodifiableList());
    }
}
