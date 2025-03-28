package com.example.booking.service;

import com.example.booking.dto.UnitDTO;
import com.example.booking.model.Booking;
import com.example.booking.model.Unit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface BookingServiceEntity
{
    Optional<Booking> getBookingById(Long id);

    void saveBooking(Booking booking);

    List<Booking> getBookingByUnitAndEndDateAfter(Unit unit, LocalDateTime localDateTime);

    List<Booking> findAll();

    Page<Booking> findAll(Specification<Booking> rootSpec, Pageable pageable);

    void deleteBooking(Booking booking);
}
