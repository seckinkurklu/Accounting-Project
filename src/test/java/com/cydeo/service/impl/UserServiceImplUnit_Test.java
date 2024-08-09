package com.cydeo.service.impl;

import com.cydeo.repository.UserRepository;
import com.cydeo.util.MapperUtil;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplUnit_Test {

    @Mock
    private UserRepository userRepository;

    @Mock
    private MapperUtil mapperUtil;
}
