package com.cydeo.service.Impl;

import com.cydeo.dto.RoleDto;
import com.cydeo.repository.RoleRepository;
import com.cydeo.service.RoleService;
import com.cydeo.util.MapperUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final MapperUtil mapperUtil;

    public RoleServiceImpl(RoleRepository roleRepository, MapperUtil mapperUtil) {
        this.roleRepository = roleRepository;
        this.mapperUtil = mapperUtil;
    }


    @Override
    public RoleDto findById(Long id) {
        return mapperUtil.convert(roleRepository.findById(id), new RoleDto());
    }

    @Override
    public List<RoleDto> listAllRoles() {
        return roleRepository.findAll().stream()
                .map(role -> mapperUtil.convert(role, new RoleDto())).toList();
    }
}
