package com.example.booking.service;

import com.example.booking.model.Booking;
import com.example.booking.model.Unit;
import com.example.booking.repository.BookingRepository;
import com.example.booking.service.impl.BookingServiceEntityImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class BookingServiceEntityTest {

    @InjectMocks
    private BookingServiceEntityImpl bookingService;

    @Mock
    private BookingRepository bookingRepository;

    @Test
    void shouldGetBookingById() {
        Booking booking = new Booking();
        booking.setId(1L);

        Mockito.when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));

        Optional<Booking> result = bookingService.getBookingById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
    }

    @Test
    void shouldSaveBooking() {
        Booking booking = new Booking();
        booking.setId(1L);

        bookingService.saveBooking(booking);

        Mockito.verify(bookingRepository, Mockito.times(1)).save(booking);
    }

    @Test
    void shouldGetBookingByUnitAndEndDateAfter() {
        Unit unit = new Unit();
        unit.setId(1L);

        Booking booking1 = new Booking();
        booking1.setUnit(unit);
        booking1.setEndDate(LocalDateTime.now().plusDays(2));

        Booking booking2 = new Booking();
        booking2.setUnit(unit);
        booking2.setEndDate(LocalDateTime.now().plusDays(3));

        List<Booking> expectedBookings = Arrays.asList(booking1, booking2);

        Mockito.when(bookingRepository.findByUnitAndEndDateAfter(Mockito.eq(unit), Mockito.any()))
                .thenReturn(expectedBookings);

        List<Booking> result = bookingService.getBookingByUnitAndEndDateAfter(unit, LocalDateTime.now());

        assertEquals(2, result.size());
        assertEquals(unit.getId(), result.get(0).getUnit().getId());
    }

    @Test
    void shouldFindAll() {
        Booking booking1 = new Booking();
        Booking booking2 = new Booking();
        List<Booking> bookings = Arrays.asList(booking1, booking2);

        Mockito.when(bookingRepository.findAll()).thenReturn(bookings);

        List<Booking> result = bookingService.findAll();

        assertEquals(2, result.size());
    }

    @Test
    void shouldFindAllWithSpecification() {
        Specification<Booking> spec = Mockito.mock(Specification.class);
        Pageable pageable = PageRequest.of(0, 10);
        Page<Booking> page = new PageImpl<>(Collections.singletonList(new Booking()));

        Mockito.when(bookingRepository.findAll(spec, pageable)).thenReturn(page);

        Page<Booking> result = bookingService.findAll(spec, pageable);

        assertEquals(1, result.getTotalElements());
    }

    @Test
    void shouldDeleteBooking() {
        Booking booking = new Booking();
        booking.setId(1L);

        bookingService.deleteBooking(booking);

        Mockito.verify(bookingRepository, Mockito.times(1)).delete(booking);
    }
}
