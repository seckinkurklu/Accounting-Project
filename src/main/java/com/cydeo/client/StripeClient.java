package com.cydeo.client;

import com.cydeo.dto.stripe_api.PaymentResponse;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@FeignClient(name = "stripeClient", url = "https://api.stripe.com/v1")
public interface StripeClient {
//
//@PostMapping("/charges")
//PaymentResponse createCharge(@RequestBody PaymentRequest paymentRequest, @RequestHeader("Authorization") String authorizationHeader);

    @PostMapping("/charges")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    PaymentResponse createCharge(
            @RequestParam("amount") int amount,
            @RequestParam("currency") String currency,
            @RequestParam("source") String source,
            @RequestParam("description") String description,
            @RequestHeader("Authorization") String authorizationHeader
    );


}

