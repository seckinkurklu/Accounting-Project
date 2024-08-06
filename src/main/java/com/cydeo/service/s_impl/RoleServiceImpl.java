package com.cydeo.service.s_impl;

import com.cydeo.dto.RoleDto;
import com.cydeo.entity.Role;
import com.cydeo.exception.RoleNotFoundException;
import com.cydeo.repository.RoleRepository;
import com.cydeo.service.RoleService;
import com.cydeo.service.UserService;
import com.cydeo.util.MapperUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final MapperUtil mapperUtil;
    private final UserService userService;

    public RoleServiceImpl(RoleRepository roleRepository, MapperUtil mapperUtil, UserService userService) {
        this.roleRepository = roleRepository;
        this.mapperUtil = mapperUtil;
        this.userService = userService;
    }


    @Override
    public RoleDto findById(Long id) {
        return mapperUtil.convert(roleRepository.findById(id), new RoleDto());
    }

    @Override
    public List<RoleDto> listAllRoles() throws RoleNotFoundException {
        return roleRepository.findAll().stream()
                .map(role -> mapperUtil.convert(role, new RoleDto())).collect(Collectors.toList());
    }


    @Override
    public List<RoleDto> listRolesByLoggedInUser ()throws RoleNotFoundException {
        RoleDto loggedInUserRole  = userService.getLoggedUser().getRole();
        List<Role> roleList = new ArrayList<>();

        if(loggedInUserRole.getDescription().equals("Admin")) {
            roleList.addAll(roleRepository.findRolesByDescriptionIn(List.of("Admin", "Manager", "Employee")));
        } else if (loggedInUserRole.getDescription().equals("Root User")){
            roleList.addAll(roleRepository.findRolesByDescriptionIn(List.of("Admin")));
        }

        return roleList.stream().map(role -> mapperUtil.convert(role, new RoleDto())).collect(Collectors.toList());
    }
}
