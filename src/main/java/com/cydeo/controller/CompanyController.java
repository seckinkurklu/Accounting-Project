package com.cydeo.controller;

import com.cydeo.dto.CompanyDto;
import com.cydeo.entity.Company;
import com.cydeo.service.CompanyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/companies")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping("/list")
    public String listAllCompanies(Model model) {
        model.addAttribute("companies", companyService.listAllCompanies());
        return "company/company-list";
    }
    @GetMapping("/create")
    public String createCompany(Model model) {
        model.addAttribute("newCompany", new CompanyDto());
        return "company/company-create";
    }
    @PostMapping("/create")
    public String createCompany(@ModelAttribute ("company") CompanyDto company, Model model) {
        companyService.save(company);
        return "redirect:/companies/list";
    }

    @GetMapping("/update/{id}")
    public String updateCompany(@PathVariable ("id") Long id, Model model) {

        CompanyDto companyDto = companyService.findById(id);
        model.addAttribute("company", companyDto);
        return "company/company-update";
    }

    @PostMapping("/update/{id}")
    public String updateCompany(@PathVariable("id") Long id, @ModelAttribute("company") CompanyDto companyDto) {
        companyDto.setId(id);
        companyService.save(companyDto);
        return "redirect:/companies/list";
    }
}
