package com.example.booking.service;

import com.example.booking.dto.UnitDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface UnitService {
    Page<UnitDTO> findAll(Map<String, Object> filters, Pageable pageable);

    void createUnit(UnitDTO unitDTO);
}
