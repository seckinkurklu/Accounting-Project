package com.cydeo.controller;

import com.cydeo.dto.PaymentDto;
import com.cydeo.entity.Payment;
import com.cydeo.service.PaymentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.Year;
import java.util.List;

@Controller
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("/list")
    public String paymentsList(@RequestParam(name="year", required = false) Integer year, Model model) {
        if (year == null) {
            year = Year.now().getValue();
        }
        model.addAttribute("year", year);
        model.addAttribute("payments", paymentService.listPaymentsForYear(year));
        return "/payment/payment-list";
    }

    @GetMapping("/newpayment/{id}")
    public String newPayment(@PathVariable Long id, Model model) {
        model.addAttribute("newpayment", new Payment());
        model.addAttribute("payment", paymentService.findById(id));
        return "/payment/payment-method";
    }

    @GetMapping("/toInvoice/{id}")
    public String newInvoice(@PathVariable Long id, Model model) {
        model.addAttribute("company", paymentService.findById(id).getCompany());
        model.addAttribute("payment", paymentService.findById(id));
        return "/payment/payment-invoice-print";
    }
}
