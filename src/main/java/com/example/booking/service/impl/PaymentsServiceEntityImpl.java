package com.example.booking.service.impl;

import com.example.booking.model.Payment;
import com.example.booking.repository.PaymentRepository;
import com.example.booking.service.PaymentServiceEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentsServiceEntityImpl implements PaymentServiceEntity {

    private final PaymentRepository paymentRepository;

    @Override
    public Payment savePayment(Payment payment) {
        return paymentRepository.save(payment);
    }
}
