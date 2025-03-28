package com.example.booking.controller;

import com.example.booking.dto.PaymentDTO;
import com.example.booking.service.PaymentService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PaymentController.class)
class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PaymentService paymentService;

    @Test
    void shouldProcessPayment() throws Exception {
        PaymentDTO paymentDTO = PaymentDTO.builder()
                .id(1L)
                .bookingId(1L)
                .amount(BigDecimal.valueOf(100.00))
                .build();

        Mockito.when(paymentService.processPayment(Mockito.anyLong(), Mockito.any()))
                .thenReturn(paymentDTO);

        mockMvc.perform(post("/payments/proccess")
                        .param("bookingId", "1")
                        .param("amount", "100.00"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bookingId").value(1))
                .andExpect(jsonPath("$.amount").value(100.00));
    }
}
