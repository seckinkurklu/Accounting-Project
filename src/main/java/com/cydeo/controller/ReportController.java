package com.cydeo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/reports")
public class ReportController {

    @GetMapping("/profitLossData")
    public String profitLossData(Model model) {
        model.addAttribute("monthlyProfitLossDataMap", )
        return "report/profitLossData";
    }
}
