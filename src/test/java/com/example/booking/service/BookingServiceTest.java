package com.example.booking.service;

import com.example.booking.dto.BookingDTO;
import com.example.booking.model.Booking;
import com.example.booking.model.Unit;
import com.example.booking.model.User;
import com.example.booking.model.specification.booking.BookingFilterResolver;
import com.example.booking.service.impl.BookingServiceImpl;
import com.example.booking.transformers.BookingToBookingDTOTransformer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BookingServiceTest {

    @InjectMocks
    private BookingServiceImpl bookingService;

    @Mock
    private BookingServiceEntity bookingServiceEntity;

    @Mock
    private UnitServiceEntity unitServiceEntity;

    @Mock
    private UserServiceEntity userServiceEntity;

    @Mock
    private EventServiceEntity eventServiceEntity;

    @Mock
    private BookingToBookingDTOTransformer bookingToBookingDTOTransformer;

    @Mock
    private BookingFilterResolver filterResolver;

    @Mock
    private RedisTemplate<String, Long> redisTemplate;

    @Mock
    private ValueOperations<String, Long> valueOperations;

    private static final String AVAILABLE_UNITS_KEY = "available_units";

    @Test
    void shouldBookUnit_Success() {
        Long unitId = 1L;
        Long userId = 2L;
        LocalDateTime startDate = LocalDateTime.now().plusDays(1);
        LocalDateTime endDate = LocalDateTime.now().plusDays(5);

        Unit unit = new Unit();
        unit.setId(unitId);
        unit.setAvailable(true);

        User user = new User();
        user.setId(userId);

        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setUnitId(unitId);
        bookingDTO.setUserId(userId);

        Mockito.when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        Mockito.when(valueOperations.get(AVAILABLE_UNITS_KEY)).thenReturn(10L);

        Mockito.when(unitServiceEntity.findById(unitId)).thenReturn(Optional.of(unit));
        Mockito.when(userServiceEntity.getUserById(userId)).thenReturn(Optional.of(user));
        Mockito.when(bookingServiceEntity.getBookingByUnitAndEndDateAfter(Mockito.any(), Mockito.any()))
                .thenReturn(Collections.emptyList());

        Mockito.when(bookingToBookingDTOTransformer.transform(Mockito.any())).thenReturn(bookingDTO);

        BookingDTO result = bookingService.bookUnit(unitId, userId, startDate, endDate);

        assertNotNull(result);
        assertEquals(unitId, result.getUnitId());
        assertEquals(userId, result.getUserId());

        Mockito.verify(bookingServiceEntity, Mockito.times(1)).saveBooking(Mockito.any());
        Mockito.verify(redisTemplate.opsForValue(), Mockito.times(1)).decrement(AVAILABLE_UNITS_KEY);
    }

    @Test
    void shouldBookUnit_NoAvailableUnits() {
        Long unitId = 1L;
        Long userId = 2L;

        Mockito.when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        Mockito.when(valueOperations.get(AVAILABLE_UNITS_KEY)).thenReturn(0L);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            bookingService.bookUnit(unitId, userId, LocalDateTime.now(), LocalDateTime.now().plusDays(2));
        });

        assertEquals("No available units", exception.getMessage());
    }

    @Test
    void shouldBookUnit_UnitNotFound() {
        Long unitId = 1L;
        Long userId = 2L;

        Mockito.when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        Mockito.when(valueOperations.get(AVAILABLE_UNITS_KEY)).thenReturn(10L);

        Mockito.when(unitServiceEntity.findById(unitId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            bookingService.bookUnit(unitId, userId, LocalDateTime.now(), LocalDateTime.now().plusDays(2));
        });

        assertEquals("Unit not found", exception.getMessage());
    }

    @Test
    void shouldCancelBooking_Success() {

        Long bookingId = 1L;
        Booking booking = new Booking();
        booking.setId(bookingId);
        Unit unit = new Unit();
        unit.setId(2L);
        unit.setAvailable(false);
        booking.setUnit(unit);

        Mockito.when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        Mockito.lenient().when(valueOperations.get(AVAILABLE_UNITS_KEY)).thenReturn(10L);
        Mockito.when(bookingServiceEntity.getBookingById(bookingId)).thenReturn(Optional.of(booking));

        bookingService.cancelBooking(bookingId);

        Mockito.verify(unitServiceEntity, Mockito.times(1)).save(unit);
        Mockito.verify(bookingServiceEntity, Mockito.times(1)).deleteBooking(booking);
        Mockito.verify(redisTemplate.opsForValue(), Mockito.times(1)).increment(AVAILABLE_UNITS_KEY);
    }

    @Test
    void shouldCancelBooking_NotFound() {
        Long bookingId = 1L;

        Mockito.when(bookingServiceEntity.getBookingById(bookingId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            bookingService.cancelBooking(bookingId);
        });

        assertEquals("Booking not found", exception.getMessage());
    }

    @Test
    void shouldGetAvailableUnitsCount_Cached() {
        Mockito.when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        Mockito.when(valueOperations.get(AVAILABLE_UNITS_KEY)).thenReturn(5L);

        long result = bookingService.getAvailableUnitsCount();

        assertEquals(5L, result);
    }

    @Test
    void shouldGetAvailableUnitsCount_Refresh() {
        Mockito.when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        Mockito.lenient().when(valueOperations.get(AVAILABLE_UNITS_KEY)).thenReturn(10L);

        Mockito.when(valueOperations.get(AVAILABLE_UNITS_KEY)).thenReturn(null, 10L);
        Mockito.when(unitServiceEntity.countByAvailableTrue()).thenReturn(10L);

        long result = bookingService.getAvailableUnitsCount();

        assertEquals(10L, result);
        Mockito.verify(valueOperations, Mockito.times(2)).get(AVAILABLE_UNITS_KEY);
        Mockito.verify(valueOperations).set(AVAILABLE_UNITS_KEY, 10L);
    }

}

