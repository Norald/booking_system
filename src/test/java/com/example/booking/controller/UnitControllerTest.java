package com.example.booking.controller;

import com.example.booking.dto.UnitDTO;
import com.example.booking.service.BookingService;
import com.example.booking.service.UnitService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UnitController.class)
class UnitControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UnitService unitService;

    @MockBean
    private BookingService bookingService;

    @Test
    void shouldGetUnits() throws Exception {
        Page<UnitDTO> units = new PageImpl<>(
                Collections.singletonList(UnitDTO.builder()
                        .id(1L)
                        .rooms(2)
                        .type("HOME")
                        .floor(5)
                        .cost(BigDecimal.valueOf(150.00))
                        .description("Nice place")
                        .build())
        );

        Mockito.when(unitService.findAll(Mockito.any(), Mockito.any()))
                .thenReturn(units);

        mockMvc.perform(get("/units")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1))
                .andExpect(jsonPath("$.content[0].rooms").value(2))
                .andExpect(jsonPath("$.content[0].type").value("HOME"))
                .andExpect(jsonPath("$.content[0].floor").value(5))
                .andExpect(jsonPath("$.content[0].cost").value(150.00))
                .andExpect(jsonPath("$.content[0].description").value("Nice place"));
    }

    @Test
    void shouldGetAvailableUnits() throws Exception {
        Mockito.when(bookingService.getAvailableUnitsCount()).thenReturn(5L);

        mockMvc.perform(get("/units/available"))
                .andExpect(status().isOk())
                .andExpect(content().string("5"));
    }

    @Test
    void shouldCreateUnit() throws Exception {
        UnitDTO unitDTO = UnitDTO.builder()
                .id(1L)
                .rooms(3)
                .type("FLAT")
                .floor(2)
                .cost(BigDecimal.valueOf(200.00))
                .description("Cozy flat")
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonContent = objectMapper.writeValueAsString(unitDTO);

        mockMvc.perform(post("/units/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isOk());

        Mockito.verify(unitService, Mockito.times(1)).createUnit(Mockito.any(UnitDTO.class));
    }
}
