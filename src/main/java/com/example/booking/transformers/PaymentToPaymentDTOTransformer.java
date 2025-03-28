package com.example.booking.transformers;

import com.example.booking.dto.PaymentDTO;
import com.example.booking.model.Payment;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.Transformer;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentToPaymentDTOTransformer implements Transformer<Payment, PaymentDTO> {
    @Override
    public PaymentDTO transform(Payment payment) {
        return PaymentDTO.builder()
                .id(payment.getId())
                .paymentDate(payment.getPaymentDate())
                .bookingId(payment.getBooking().getId())
                .amount(payment.getAmount())
                .build();
    }
}
