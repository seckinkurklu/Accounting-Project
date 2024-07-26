package com.cydeo.controller;


import com.cydeo.dto.UserDto;
import com.cydeo.service.CompanyService;
import com.cydeo.service.RoleService;
import com.cydeo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final RoleService roleService;
    private final CompanyService companyService;

    public UserController(UserService userService, RoleService roleService, CompanyService companyService) {
        this.userService = userService;
        this.roleService = roleService;
        this.companyService = companyService;
    }

    @GetMapping("/list")
    public String listAllUsers(Model model) {

        model.addAttribute("users", userService.getLoggedUser());
        return "/user/user-list";
    }

    @GetMapping("/create")
    public String CreateUser(Model model) {
        model.addAttribute("newUser", new UserDto());
        model.addAttribute("userRoles", roleService.listAllRoles());
        model.addAttribute("companies", userService.listAllUsers());

        return "/user/user-create";
    }

    @PostMapping("/create")
    public String CreateUser(@Valid @ModelAttribute("newUser") UserDto user, BindingResult bindingResult, Model model) {
        boolean emailExist = userService.findByUsernameCheck(user.getUsername());
        if (bindingResult.hasErrors()) {
            if (emailExist) {
                bindingResult.rejectValue("username", " ", "A user with this email already exists. Please try with different email.");
            }

            model.addAttribute("userRoles", roleService.listAllRoles());
            model.addAttribute("companies", userService.listAllUsers());

            return "/user/user-create";
        }
        userService.save(user);
        return "redirect:/users/list";
    }

    @GetMapping("/update/{id}")
    public String UpdateUser(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.findById(id));

        model.addAttribute("userRoles", roleService.listAllRoles());
        model.addAttribute("companies", companyService.listAllCompanies());
        model.addAttribute("users", userService.listAllUsers());
        return "/user/user-update";
    }
    @PostMapping("/update/{id}")
    public String UpdateUser(@Valid @ModelAttribute("user") UserDto userDto,  BindingResult bindingResult, Model model,@PathVariable("id") Long id ) {
       if (bindingResult.hasErrors()) {
           model.addAttribute("userRoles", roleService.listAllRoles());
           model.addAttribute("companies", companyService.listAllCompanies());
           model.addAttribute("users", userService.listAllUsers());
           return "/user/user-update";
       }
       userService.save(userDto);
        return"redirect:/users/create";
    }

}







