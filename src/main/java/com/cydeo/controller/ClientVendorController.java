package com.cydeo.controller;

import com.cydeo.dto.ClientVendorDto;
import com.cydeo.enums.ClientVendorType;
import com.cydeo.service.ClientVendorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

        model.addAttribute("clientVendors",clientVendorService.listAllByCompanyTitle());
        return "/clientVendor/clientVendor-list";
    }


    @GetMapping("/create")
    public String createClientVendor(Model model){

        model.addAttribute("newClientVendor",new ClientVendorDto());

        List<ClientVendorType> clientVendorTypes = Arrays.asList(ClientVendorType.values()); // clientVendor types added as like on html file.
        model.addAttribute("clientVendorTypes",clientVendorTypes); // types are taken to UI
        return "/clientVendor/clientVendor-create";

    }

    @PostMapping("/create")
    public String insertClientVendor(@ModelAttribute("newClientVendor")ClientVendorDto clientVendorDto){

        clientVendorService.save(clientVendorDto);
        return "redirect:/clientVendors/list";
    }

@GetMapping("/update/{id}")
public String editClientVendor(@PathVariable("id") Long id, Model model) {
    List<ClientVendorType> clientVendorTypes = Arrays.asList(ClientVendorType.values());
    model.addAttribute("clientVendor", clientVendorService.findById(id));
    model.addAttribute("clientVendorTypes", clientVendorTypes);

    List<String> countries = Arrays.asList("United States");
    model.addAttribute("countries", countries);

    return "/clientVendor/clientVendor-update";
}

    @PostMapping("/update/{id}")
    public String updateClientVendor(@ModelAttribute("clientVendor") ClientVendorDto clientVendorDto){
        clientVendorService.update(clientVendorDto);
        return "redirect:/clientVendors/list";
    }
    @GetMapping("/delete/{id}")
    public String deleteClientVendor(@PathVariable("id") Long id){

        clientVendorService.delete(id);

        return "redirect:/clientVendors/list";
    }
}
