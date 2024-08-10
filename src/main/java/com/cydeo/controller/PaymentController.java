package com.cydeo.controller;

import com.cydeo.client.StripeClient;
import com.cydeo.dto.CompanyDto;
import com.cydeo.dto.InvoiceDto;
import com.cydeo.dto.PaymentDto;
import com.cydeo.enums.Currency;
import com.cydeo.service.CompanyService;
import com.cydeo.service.InvoiceService;
import com.cydeo.service.PaymentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.Year;

@Controller
@RequestMapping("/payments")
public class PaymentController {


    private final String apiKey = "pk_test_51Pj1miJfAsxFag9w7Rixsk1npiG0XGuCNYNWBgeecinaS9NNpROROSUvDUEnRsltP6rEsUrfCTsSfUGblv9AyRsH00RTeD2gR7";
    private final PaymentService paymentService;
    private final StripeClient stripeClient;
    private final CompanyService companyService;
    private final InvoiceService invoiceService;

    public PaymentController(PaymentService paymentService, StripeClient stripeClient, CompanyService companyService, InvoiceService invoiceService) {
        this.paymentService = paymentService;
        this.stripeClient = stripeClient;
        this.companyService = companyService;
        this.invoiceService = invoiceService;
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

//    @GetMapping("/newpayment/{id}")
//    public String newPayment(@PathVariable Long id, Model model) {
//        model.addAttribute("newpayment", new Payment());
//        model.addAttribute("payment", paymentService.findById(id));
//        return "/payment/payment-method";
//    }

    @GetMapping ("/newpayment/{id}")
    public String newPayment(@PathVariable Long id, Model model) {

        PaymentDto payment = paymentService.findById(id);
        payment.setAmount(new BigDecimal("250.00"));
        model.addAttribute("payment",payment);
        model.addAttribute("monthId", payment.getMonth().getId());
        model.addAttribute("stripePublicKey",apiKey);
        model.addAttribute("currency",Currency.USD.getValue());


        return "/payment/payment-method";
    }


    @PostMapping("/charge/{id}")
    public String charge(@PathVariable("id") Long monthId,
                         @RequestParam("amount") BigDecimal amount,
                         @RequestParam("stripeToken") String stripeToken,
                         Model model) {

        PaymentDto paymentDto = paymentService.findById(monthId);
        paymentDto.setAmount(amount);
        paymentDto.setCompanyStripeId(stripeToken);
        paymentDto.setDescription("Subscription Fee for month ID: " + monthId);

// makePayment
        paymentDto = paymentService.processPayment(paymentDto);

        // show result on UI.
        model.addAttribute("monthId", monthId);
        model.addAttribute("payment", paymentDto.getAmount());
        model.addAttribute("stripePublicKey", apiKey);
        model.addAttribute("currency", Currency.USD.getValue());
        model.addAttribute("chargeId", paymentDto.getCompanyStripeId());
        model.addAttribute("description", paymentDto.getDescription());


        return "payment/payment-result";
    }

    @GetMapping("/toInvoice/{id}")
    public String newInvoice(@PathVariable Long id, Model model) {
        model.addAttribute("company", invoiceService.getInvoiceById(id).getCompany());
        model.addAttribute("payment", paymentService.findById(id));
        return "/payment/payment-invoice-print";
    }


}
