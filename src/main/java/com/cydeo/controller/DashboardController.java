package com.cydeo.controller;

import com.cydeo.annotation.ExecutionTime;
import com.cydeo.client.CurrencyClient;
import com.cydeo.service.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
When using @RequiredArgsConstructor,
it automatically creates a constructor for all final
fields in your class, ensuring that they are not null.
Therefore, you don't need to add @NonNull to those final fields since @RequiredArgsConstructor will ensure they are not null.
 */
@Controller
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final CurrencyService currencyService;

    @ExecutionTime
    @GetMapping()
    public String getExchangeRates(Model model) {

        model.addAttribute("exchangeRates", currencyService.getExchangeRates());

        return "/dashboard";

    }

}
