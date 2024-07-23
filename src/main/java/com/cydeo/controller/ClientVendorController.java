package com.cydeo.controller;

import com.cydeo.dto.ClientVendorDto;
import com.cydeo.service.ClientVendorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/clientVendors")
public class ClientVendorController {
    private final ClientVendorService clientVendorService;

    public ClientVendorController(ClientVendorService clientVendorService) {
        this.clientVendorService = clientVendorService;
    }


    @GetMapping("/list")
    public String getClientVendors(Model model){

        model.addAttribute("clientVendor",clientVendorService.listAllClientVendor());

        return "/clientVendor/clientVendor-list";


    }



}
