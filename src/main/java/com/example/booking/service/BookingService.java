package com.example.booking.service;

import com.example.booking.dto.BookingDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Map;

public interface BookingService {
    BookingDTO bookUnit(Long unitId, Long userId, LocalDateTime startDate, LocalDateTime endDate);

    void cancelBooking(Long bookingId);

    long getAvailableUnitsCount();

    Page<BookingDTO> findAll(Map<String, Object> filters, Pageable pageable);
}
