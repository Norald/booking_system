package com.example.booking.controller;


import com.example.booking.dto.PaymentDTO;
import com.example.booking.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/proccess")
    public ResponseEntity<PaymentDTO> proccessPayment(@RequestParam Long bookingId, @RequestParam BigDecimal amount) {
        return ResponseEntity.ok(paymentService.processPayment(bookingId, amount));
    }
}
