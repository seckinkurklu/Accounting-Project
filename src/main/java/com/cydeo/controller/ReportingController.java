package com.cydeo.controller;

import com.cydeo.service.ReportingService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/reports")
public class ReportingController {
    private final ReportingService reportService;

    public ReportingController(ReportingService reportService) {
        this.reportService = reportService;
    }


    @GetMapping("/profitLossData")
    public String profitLossData(Model model) {
        model.addAttribute("monthlyProfitLossDataMap", reportService.getMonthlyProfitLossByCompany());
        return "report/profit-loss-report";
    }

    @GetMapping("/stockData")
    public String stockDateList(Model model){
model.addAttribute("invoiceProducts",reportService.getInvoiceProductList());
        return "report/stock-report";
    }
}
