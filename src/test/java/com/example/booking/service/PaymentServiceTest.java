package com.example.booking.service;

import com.example.booking.dto.PaymentDTO;
import com.example.booking.model.Booking;
import com.example.booking.model.Payment;
import com.example.booking.service.impl.PaymentServiceImpl;
import com.example.booking.transformers.PaymentToPaymentDTOTransformer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @InjectMocks
    private PaymentServiceImpl paymentService;

    @Mock
    private PaymentServiceEntity paymentServiceEntity;

    @Mock
    private BookingServiceEntity bookingServiceEntity;

    @Mock
    private PaymentToPaymentDTOTransformer paymentToPaymentDTOTransformer;

    @Test
    void shouldProcessPayment_Success() {
        Long bookingId = 1L;
        BigDecimal amount = new BigDecimal("100.00");

        Booking booking = new Booking();
        booking.setId(bookingId);
        booking.setPaid(false);

        Payment payment = new Payment();
        payment.setBooking(booking);
        payment.setAmount(amount);
        payment.setPaymentDate(LocalDateTime.now());

        PaymentDTO paymentDTO = new PaymentDTO();

        Mockito.when(bookingServiceEntity.getBookingById(bookingId)).thenReturn(Optional.of(booking));
        Mockito.when(paymentServiceEntity.savePayment(Mockito.any())).thenReturn(payment);
        Mockito.when(paymentToPaymentDTOTransformer.transform(Mockito.any())).thenReturn(paymentDTO);

        PaymentDTO result = paymentService.processPayment(bookingId, amount);

        assertNotNull(result);
        assertTrue(booking.isPaid());

        Mockito.verify(bookingServiceEntity, Mockito.times(1)).saveBooking(booking);
        Mockito.verify(paymentServiceEntity, Mockito.times(1)).savePayment(Mockito.any());
    }

    @Test
    void shouldThrowException_WhenBookingNotFound() {
        Long bookingId = 1L;
        BigDecimal amount = new BigDecimal("100.00");

        Mockito.when(bookingServiceEntity.getBookingById(bookingId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            paymentService.processPayment(bookingId, amount);
        });

        assertEquals("Booking not found", exception.getMessage());
        Mockito.verify(bookingServiceEntity, Mockito.never()).saveBooking(Mockito.any());
    }
}
