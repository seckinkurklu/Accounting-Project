package com.cydeo.controller;

import com.cydeo.client.CurrencyClient;
import com.cydeo.service.CurrencyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    private final CurrencyService currencyService;

    public DashboardController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping()
    public String getExchangeRates(Model model) {

        model.addAttribute("exchangeRates", currencyService.getExchangeRates());

        return "/dashboard";

    }

}
