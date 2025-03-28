package com.example.booking.transformers;

import com.example.booking.dto.PaymentDTO;
import com.example.booking.model.Booking;
import com.example.booking.model.Payment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class PaymentToPaymentDTOTransformerTest {

    private PaymentToPaymentDTOTransformer transformer;

    @BeforeEach
    void setUp() {
        transformer = new PaymentToPaymentDTOTransformer();
    }

    @Test
    void shouldTransformPaymentToDTO() {
        Payment payment = Payment.builder()
                .id(1L)
                .booking(Booking.builder().id(2L).build())
                .amount(BigDecimal.valueOf(100.00))
                .paymentDate(LocalDateTime.now())
                .build();

        PaymentDTO result = transformer.transform(payment);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(2L, result.getBookingId());
        assertEquals(BigDecimal.valueOf(100.00), result.getAmount());
    }
}