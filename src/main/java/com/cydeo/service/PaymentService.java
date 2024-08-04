package com.cydeo.service;

import com.cydeo.dto.PaymentDto;

import java.util.List;

public interface PaymentService {

    PaymentDto findById(Long id);

    List<PaymentDto> findAll();

    PaymentDto save(PaymentDto paymentDto);

    PaymentDto update(PaymentDto paymentDto);

    void deleteById(Long id);

}
