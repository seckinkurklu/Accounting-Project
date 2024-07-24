package com.cydeo.converter;

import com.cydeo.dto.RoleDto;
import com.cydeo.service.RoleService;
import org.modelmapper.spi.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;


@Component
@ConfigurationPropertiesBinding
public class RoleDTOConverter implements Converter<String, RoleDto> {

    RoleService roleService;

    public RoleDTOConverter(@Lazy RoleService roleService) {

        this.roleService = roleService;
    }


    @Override
    public RoleDto convert(String source) {

        if (source == null || source.equals("")) {  //  Select  -> ""
            return null;
        }

        return roleService.findById(Long.parseLong(source));

    }

}
