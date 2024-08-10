package com.cydeo.service.s_impl;

import com.cydeo.client.StripeClient;
import com.cydeo.dto.PaymentDto;
import com.cydeo.dto.stripe_api.PaymentResponse;
import com.cydeo.entity.Payment;
import com.cydeo.enums.Currency;
import com.cydeo.enums.Months;
import com.cydeo.repository.PaymentRepository;
import com.cydeo.service.PaymentService;
import com.cydeo.util.MapperUtil;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final MapperUtil mapperUtil;
    private final StripeClient stripeClient;
    private final String stripeSecretKey="sk_test_51Pj1miJfAsxFag9wDrFBgM900trAZmEjbBoboPw78KNl6AysGXwNO4PlgqDWLVU4gkEbIdb862DXW9650lQKfQdD00AZZGcWOF";

    public PaymentServiceImpl(PaymentRepository paymentRepository, MapperUtil mapperUtil, StripeClient stripeClient) {
        this.paymentRepository = paymentRepository;
        this.mapperUtil = mapperUtil;
        this.stripeClient = stripeClient;
    }

    @Override
    public PaymentDto findById(Long id) {
        Payment payment = paymentRepository.findById(id).orElseThrow(() -> new RuntimeException("Payment not found"));
        return mapperUtil.convert(payment, new PaymentDto());
    }

    @Override
    public List<PaymentDto> findAll() {
        return paymentRepository.findAll().stream().map(payment -> mapperUtil.convert(payment, new PaymentDto()))
                .collect(Collectors.toList());
    }

    @Override
    public PaymentDto save(PaymentDto paymentDto) {
        Payment payment = mapperUtil.convert(paymentDto, new Payment());
        Payment savedPayment = paymentRepository.save(payment);

        return mapperUtil.convert(savedPayment, new PaymentDto());
    }

    @Override
    public PaymentDto update(PaymentDto paymentDto) {
        Payment payment = mapperUtil.convert(paymentDto, new Payment());
        Payment updatedPayment = paymentRepository.save(payment);

        return mapperUtil.convert(updatedPayment, new PaymentDto());
    }

    @Override
    public void deleteById(Long id) {
        paymentRepository.deleteById(id);
    }

    @Override
    public List<PaymentDto> listPaymentsForYear(int year) {
        List<Payment> payments = paymentRepository.findAllByYear(year);

        if (payments.isEmpty()) {
            createPaymentsForYear(year);
            payments = paymentRepository.findAllByYear(year);
        }
        return payments.stream().map(p->mapperUtil.convert(p, new PaymentDto()))
                .sorted(Comparator.comparing(payment -> payment.getMonth().ordinal())).toList();
    }



    @Override
    public PaymentDto processPayment(PaymentDto paymentDto) {
        int amount = paymentDto.getAmount().multiply(new BigDecimal(100)).intValue();
        String currency = Currency.USD.getValue();
        String source = paymentDto.getCompanyStripeId();
        String description = paymentDto.getDescription();

        String authorizationHeader = "Bearer " + stripeSecretKey;
        PaymentResponse response = stripeClient.createCharge(amount, currency, source, description, authorizationHeader);

        if (response.isPaid()) {
            paymentDto.setPaid(true);
            paymentDto.setCompanyStripeId(response.getId());
            paymentDto.setDescription( response.getDescription());
        } else {
            paymentDto.setPaid(false);
            paymentDto.setDescription("Transaction failed. Please try again!");
        }

        paymentRepository.save(mapperUtil.convert(paymentDto,new Payment()));

        return paymentDto;
    }



    private void createPaymentsForYear(int year) {
        List<Payment> payments = new ArrayList<>();
        for (Months month : Months.values()) {
            Payment payment = new Payment();
            payment.setYear(year);
            payment.setMonth(month);
            payment.setAmount(BigDecimal.valueOf(250));
            payment.setPaid(false);
            payments.add(payment);
        }
        paymentRepository.saveAll(payments);
    }
}
