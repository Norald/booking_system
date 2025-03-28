package com.example.booking.controller;

import com.example.booking.dto.BookingDTO;
import com.example.booking.service.BookingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookingController.class)
class BookingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookingService bookingService;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void shouldReturnBookings() throws Exception {
        mockMvc.perform(get("/bookings"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldBookUnit() throws Exception {
        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setUnitId(1L);
        bookingDTO.setUserId(2L);
        bookingDTO.setStartDate(LocalDateTime.of(2025, 3, 20, 14, 0));
        bookingDTO.setEndDate(LocalDateTime.of(2025, 3, 25, 10, 0));

        Mockito.when(bookingService.bookUnit(Mockito.anyLong(), Mockito.anyLong(), Mockito.any(), Mockito.any()))
                .thenReturn(bookingDTO);

        mockMvc.perform(post("/bookings/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookingDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.unitId").value(1))
                .andExpect(jsonPath("$.userId").value(2));
    }

    @Test
    void shouldCancelBooking() throws Exception {
        mockMvc.perform(delete("/bookings/1"))
                .andExpect(status().isOk());

        verify(bookingService, times(1)).cancelBooking(1L);
    }
}
