
package com.cydeo.controller;

import com.cydeo.service.CompanyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @GetMapping("/activate/{company-id}")
    public String activateCompany(@PathVariable("company-id") long company_id, Model model){
        companyService.activateCompany(company_id);
        return "redirect:/companies/list";
    }
    @GetMapping("/activate/{company-id}")
    public String deactivateCompany(@PathVariable("company-id") long company_id, Model model){
        companyService.deactivateCompany(company_id);
        return "redirect:/companies/list";
    }
}
