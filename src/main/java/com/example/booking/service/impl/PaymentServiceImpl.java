package com.example.booking.service.impl;

import com.example.booking.dto.PaymentDTO;
import com.example.booking.model.Booking;
import com.example.booking.model.Payment;
import com.example.booking.service.BookingServiceEntity;
import com.example.booking.service.PaymentService;
import com.example.booking.service.PaymentServiceEntity;
import com.example.booking.transformers.PaymentToPaymentDTOTransformer;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentServiceEntity paymentServiceEntity;
    private final BookingServiceEntity bookingServiceEntity;
    private final PaymentToPaymentDTOTransformer paymentToPaymentDTOTransformer;

    @Transactional
    @Override
    public PaymentDTO processPayment(Long bookingId, BigDecimal amount) {
        Booking booking = bookingServiceEntity.getBookingById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        Payment payment = Payment.builder()
                .booking(booking)
                .amount(amount)
                .paymentDate(LocalDateTime.now())
                .build();

        booking.setPaid(true);
        bookingServiceEntity.saveBooking(booking);
        return paymentToPaymentDTOTransformer.transform(paymentServiceEntity.savePayment(payment));
    }

}
