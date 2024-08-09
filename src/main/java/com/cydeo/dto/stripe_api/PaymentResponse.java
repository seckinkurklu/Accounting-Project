package com.cydeo.dto.stripe_api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponse {

    private String id;
    private String description;
    private boolean isPaid;
//    private Integer amount ;
//    private String paymentEmail;
//    private String stripeToken;


}
