package com.cydeo.service.s_impl;

import com.cydeo.dto.PaymentDto;
import com.cydeo.entity.Payment;
import com.cydeo.enums.Months;
import com.cydeo.repository.PaymentRepository;
import com.cydeo.service.PaymentService;
import com.cydeo.util.MapperUtil;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final MapperUtil mapperUtil;

    public PaymentServiceImpl(PaymentRepository paymentRepository, MapperUtil mapperUtil) {
        this.paymentRepository = paymentRepository;
        this.mapperUtil = mapperUtil;
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
