package com.example.booking.service;

import com.example.booking.dto.PaymentDTO;
import com.example.booking.model.Payment;

import java.math.BigDecimal;

public interface PaymentService {
    PaymentDTO processPayment(Long bookingId, BigDecimal amount);
}
