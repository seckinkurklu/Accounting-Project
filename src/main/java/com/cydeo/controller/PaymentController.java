package com.cydeo.controller;

import com.cydeo.client.StripeClient;
import com.cydeo.dto.PaymentDto;
import com.cydeo.dto.stripe_api.PaymentRequest;
import com.cydeo.dto.stripe_api.PaymentResponse;
import com.cydeo.enums.Currency;
import com.cydeo.enums.Months;
import com.cydeo.service.PaymentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.Year;

@Controller
@RequestMapping("/payments")
public class PaymentController {


    private final String apiKey ="pk_test_51Pj1miJfAsxFag9w7Rixsk1npiG0XGuCNYNWBgeecinaS9NNpROROSUvDUEnRsltP6rEsUrfCTsSfUGblv9AyRsH00RTeD2gR7";
    private final PaymentService paymentService;
    private final StripeClient stripeClient;

    public PaymentController(PaymentService paymentService, StripeClient stripeClient) {
        this.paymentService = paymentService;
        this.stripeClient = stripeClient;
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



//    @PostMapping("/charge/{monthId}")
//    public String charge(@PathVariable("monthId") Long monthId,
//                         @RequestParam("stripeEmail") String stripeEmail,
//                         @RequestParam("stripeToken") String stripeToken,
//                         @RequestParam("paymentId") Long paymentId,
//                         @ModelAttribute("payment") PaymentDto payment,
//                         Model model) {
//        try {
//            // PaymentRequest nesnesi oluşturuyoruz
//            PaymentRequest paymentRequest = new PaymentRequest();
//            paymentRequest.setEmail(stripeEmail);
//            paymentRequest.setToken(stripeToken);
//            paymentRequest.setAmount(payment.getAmount());
//            paymentRequest.setCurrency("USD");
//
//            // Payment servisini kullanarak ödeme işlemini gerçekleştiriyoruz
//            PaymentResponse paymentResponse = paymentService.charge(payment);
//
//            // Ödeme başarılıysa, başarı sayfasına yönlendiriyoruz
//            model.addAttribute("chargeId", paymentResponse.getId());
//            model.addAttribute("description", paymentResponse.getDescription());
//            return "payment/payment-result";
//        } catch (PaymentNotFoundException e) {
//            // Ödeme başarısızsa, hata mesajını model'e ekleyip hata sayfasına yönlendiriyoruz
//            model.addAttribute("error", e.getMessage());
//            return "payment/payment-method";
//        }
//    }


//    @PostMapping("/charge/{monthId}")
//    public String charge(@PathVariable("monthId") Long monthId,
//                         @RequestParam("stripeEmail") String stripeEmail,
//                         @RequestParam("stripeToken") String stripeToken,
//                         @ModelAttribute("payment") PaymentDto payment,
//                         Model model) {
//        try {
//            if (payment.getId() == null) {
//                throw new IllegalArgumentException("Payment ID must not be null");
//            }
//
//            PaymentResponse paymentResponse = paymentService.charge(payment, stripeEmail, stripeToken);
//
//            model.addAttribute("chargeId", paymentResponse.getId());
//            model.addAttribute("description", paymentResponse.getDescription());
//            paymentService.save(payment);
//            return "payment/payment-result";
//        } catch (PaymentNotFoundException | IllegalArgumentException e) {
//            model.addAttribute("error", e.getMessage());
//            return "payment/payment-method";
//        }
//    }
//@PostMapping("/charge/{id}")
//public String charge(@PathVariable("id") Long id, @RequestParam("amount") int amount, Model model) {
//    PaymentRequest chargeRequest = new PaymentRequest();
//    chargeRequest.setAmount(amount);
//    chargeRequest.set("USD");
//    chargeRequest.setSource("tok_visa");
//
//    String authHeader = "Bearer " + stripeSecretKey;
//
//    try {
//        ChargeResponse chargeResponse = stripeClient.createCharge(authHeader, chargeRequest);
//        model.addAttribute("chargeId", chargeResponse.getId());
//        model.addAttribute("description", chargeResponse.getDescription());
//        return "success";
//    } catch (Exception e) {
//        model.addAttribute("error", "Transaction failed. Please try again!");
//        return "failure";
//    }
//}

//    @PostMapping("/charge")
//    public PaymentResponse chargeCard(@RequestBody PaymentRequest request) {
//        String authHeader = "Bearer " + apiKey;
//        return stripeClient.createCharge(authHeader, request);
//    }

//    @PostMapping("/charge/{monthId}")
//    public String charge(@PathVariable String monthId, @RequestParam("amount") Long amount, @RequestParam("token") String token) {
//        try {
//            PaymentRequest chargeRequest = new PaymentRequest();
//            chargeRequest.setAmount(BigDecimal.valueOf(amount));  // Ödeme miktarını cent olarak ayarlayın, örneğin 25000 ise 250.00 USD
//            chargeRequest.setCurrency("USD");
//            chargeRequest.setToken(token);  // Frontend'den alınan Stripe token
//
//            // PaymentService üzerinden ödeme işlemi gerçekleştiriliyor
//            paymentService.chargePayment(chargeRequest);
//            return "payment/payment-method";
//        } catch (Exception e) {
//            return "Payment failed: " + e.getMessage();
//        }
//    }

//    @PostMapping("/charge/{id}")
//    public String charge(@PathVariable("id") Long monthId, PaymentDto paymentDto, Model model) {
//        paymentDto = paymentService.processPayment(paymentDto);
//
//        model.addAttribute("monthId", paymentDto.getMonth().getId());
//        model.addAttribute("payment",paymentDto.getAmount());
//        model.addAttribute("stripePublicKey",apiKey);
//        model.addAttribute("currency",Currency.USD.getValue());
//
//        return "payment/payment-result"; // Thymeleaf template for showing the payment result
//    }
@PostMapping("/charge/{id}")
public String charge(@PathVariable("id") Long monthId,
                     @RequestParam("amount") BigDecimal amount,
                     @RequestParam("stripeToken") String stripeToken,
                     @RequestParam("stripeEmail") String stripeEmail,
                     Model model) {
    // PaymentDto'yu oluştur ve gerekli bilgileri doldur
    PaymentDto paymentDto = new PaymentDto();
    paymentDto.setMonth(paymentDto.getMonth()); // Varsayılan olarak PaymentDto'da böyle bir alan olmalı
    paymentDto.setAmount(amount);
    paymentDto.setCompanyStripeId(stripeToken);
    paymentDto.setDescription("Subscription Fee for month ID: " + monthId);

// Servis katmanıyla ödeme işlemini gerçekleştir
    paymentDto = paymentService.processPayment(paymentDto);

    // Sonuçları model'e ekleyerek sonuç sayfasına geç
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
        model.addAttribute("company", paymentService.findById(id).getCompany());
        model.addAttribute("payment", paymentService.findById(id));
        return "/payment/payment-invoice-print";
    }
}
