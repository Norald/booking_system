package com.example.booking.service.impl;

import com.example.booking.dto.BookingDTO;
import com.example.booking.dto.UnitDTO;
import com.example.booking.model.*;
import com.example.booking.model.specification.booking.BookingFilterResolver;
import com.example.booking.service.*;
import com.example.booking.transformers.BookingToBookingDTOTransformer;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingServiceEntity bookingServiceEntity;
    private final UnitServiceEntity unitServiceEntity;
    private final UserServiceEntity userServiceEntity;
    private final EventServiceEntity eventServiceEntity;
    private final BookingToBookingDTOTransformer bookingToBookingDTOTransformer;
    private final BookingFilterResolver filterResolver;
    private final RedisTemplate<String, Long> redisTemplate;

    private static final String AVAILABLE_UNITS_KEY = "available_units";

    @Transactional
    @Override
    public BookingDTO bookUnit(Long unitId, Long userId, LocalDateTime startDate, LocalDateTime endDate) {
        Object availableUnits = redisTemplate.opsForValue().get(AVAILABLE_UNITS_KEY);
        if (availableUnits != null && ((Number) availableUnits).longValue() == 0) {
            throw new RuntimeException("No available units");
        }

        Unit unit = unitServiceEntity.findById(unitId).orElseThrow(() -> new RuntimeException("Unit not found"));
        User user = userServiceEntity.getUserById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        List<Booking> existingBookings = bookingServiceEntity.getBookingByUnitAndEndDateAfter(unit, LocalDateTime.now());
        if (!existingBookings.isEmpty()) {
            throw new RuntimeException("Unit is already booked for the given dates");
        }

        unit.setAvailable(false);
        unitServiceEntity.save(unit);

        Booking booking = Booking.builder()
                .unit(unit)
                .user(user)
                .startDate(startDate)
                .endDate(endDate)
                .paid(false)
                .createdAt(LocalDateTime.now())
                .build();

        eventServiceEntity.addEvent(Event.builder()
                .unit(unit)
                .user(user)
                .eventType(EventType.SUCCESS_BOOKING)
                .timestamp(LocalDateTime.now())
                .build());

        bookingServiceEntity.saveBooking(booking);
        decrementAvailableUnits();

        return bookingToBookingDTOTransformer.transform(booking);
    }

    @Transactional
    @Override
    public void cancelBooking(Long bookingId) {
        Booking booking = bookingServiceEntity.getBookingById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        Unit unit = booking.getUnit();
        unit.setAvailable(true);
        unitServiceEntity.save(unit);

        eventServiceEntity.addEvent(Event.builder()
                .unit(unit)
                .user(booking.getUser())
                .eventType(EventType.FAILURE_BOOKING)
                .timestamp(LocalDateTime.now())
                .build());

        bookingServiceEntity.deleteBooking(booking);
        incrementAvailableUnits();
    }

    @Scheduled(fixedRate = 60000)
    public void refreshAvailableUnitsCount() {
        long count = unitServiceEntity.countByAvailableTrue();
        redisTemplate.opsForValue().set(AVAILABLE_UNITS_KEY, count);
    }

    @Override
    public long getAvailableUnitsCount() {
        Object countObj = redisTemplate.opsForValue().get(AVAILABLE_UNITS_KEY);

        if (countObj == null) {
            refreshAvailableUnitsCount();
            countObj = redisTemplate.opsForValue().get(AVAILABLE_UNITS_KEY);
        }

        if (countObj instanceof Integer) {
            return ((Integer) countObj).longValue();
        } else if (countObj instanceof Long) {
            return (Long) countObj;
        } else {
            throw new IllegalStateException("Unexpected data type in Redis: " + countObj.getClass());
        }
    }

    private void decrementAvailableUnits() {
        redisTemplate.opsForValue().decrement(AVAILABLE_UNITS_KEY);
    }

    private void incrementAvailableUnits() {
        redisTemplate.opsForValue().increment(AVAILABLE_UNITS_KEY);
    }

    @Override
    public Page<BookingDTO> findAll(Map<String, Object> filters, Pageable pageable) {
        Specification<Booking> rootSpec = filterResolver.resolveAll(filters);
        Page<Booking> bookingEntities = bookingServiceEntity.findAll(rootSpec, pageable);

        List<BookingDTO> events = bookingEntities.getContent().stream()
                .map(bookingToBookingDTOTransformer::transform)
                .collect(Collectors.toList());

        return new PageImpl<>(events, pageable, bookingEntities.getTotalElements());
    }
}
