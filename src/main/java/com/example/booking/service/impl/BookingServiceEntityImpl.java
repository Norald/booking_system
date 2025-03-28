package com.example.booking.service.impl;

import com.example.booking.model.Booking;
import com.example.booking.model.Unit;
import com.example.booking.repository.BookingRepository;
import com.example.booking.service.BookingServiceEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookingServiceEntityImpl implements BookingServiceEntity {

    private final BookingRepository bookingRepository;

    @Override
    public Optional<Booking> getBookingById(Long id) {
        return bookingRepository.findById(id);
    }

    @Override
    public void saveBooking(Booking booking) {
        bookingRepository.save(booking);
    }

    @Override
    public List<Booking> getBookingByUnitAndEndDateAfter(Unit unit, LocalDateTime localDateTime){
        return bookingRepository.findByUnitAndEndDateAfter(unit, LocalDateTime.now());
    }

    @Override
    public List<Booking> findAll() {
        return bookingRepository.findAll();
    }

    @Override
    public Page<Booking> findAll(Specification<Booking> rootSpec, Pageable pageable) {
        return bookingRepository.findAll(rootSpec, pageable);
    }

    @Override
    public void deleteBooking(Booking booking) {
        bookingRepository.delete(booking);
    }
}
