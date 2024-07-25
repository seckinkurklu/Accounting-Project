package com.cydeo.controller;

import com.cydeo.dto.ClientVendorDto;
import com.cydeo.enums.ClientVendorType;
import com.cydeo.service.ClientVendorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.List;


@Controller
@RequestMapping("/clientVendors")
public class ClientVendorController {
    private final ClientVendorService clientVendorService;

    public ClientVendorController(ClientVendorService clientVendorService) {
        this.clientVendorService = clientVendorService;
    }


    @GetMapping("/list")
    public String getClientVendors(Model model){

        model.addAttribute("clientVendors",clientVendorService.listAllClientVendor());

        return "/clientVendor/clientVendor-list";


    }


    @GetMapping("/create")
    public String createClientVendor(Model model){

        model.addAttribute("clientVendors",new ClientVendorDto());
        List<ClientVendorType> clientVendorTypes = Arrays.asList(ClientVendorType.values());

        model.addAttribute("clientVendorTypes", clientVendorTypes);




        return "/clientVendor/clientVendor-create";

    }




}
