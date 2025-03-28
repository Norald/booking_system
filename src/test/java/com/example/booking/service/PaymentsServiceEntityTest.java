package com.example.booking.service;

import com.example.booking.model.Payment;
import com.example.booking.repository.PaymentRepository;
import com.example.booking.service.impl.PaymentsServiceEntityImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class PaymentsServiceEntityTest {
    @InjectMocks
    private PaymentsServiceEntityImpl paymentsServiceEntity;

    @Mock
    private PaymentRepository paymentRepository;

    @Test
    void shouldSavePayment() {
        Payment payment = new Payment();
        Mockito.when(paymentRepository.save(payment)).thenReturn(payment);

        Payment savedPayment = paymentsServiceEntity.savePayment(payment);

        assertNotNull(savedPayment);
        Mockito.verify(paymentRepository, Mockito.times(1)).save(payment);
    }
}
