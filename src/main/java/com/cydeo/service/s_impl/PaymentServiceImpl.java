package com.cydeo.service.s_impl;

import com.cydeo.dto.PaymentDto;
import com.cydeo.entity.Payment;
import com.cydeo.repository.PaymentRepository;
import com.cydeo.service.PaymentService;
import com.cydeo.util.MapperUtil;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
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
}
