package com.example.booking.transformers;

import com.example.booking.dto.BookingDTO;
import com.example.booking.model.Booking;
import com.example.booking.model.Unit;
import com.example.booking.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class BookingToBookingDTOTransformerTest {

    private BookingToBookingDTOTransformer transformer;

    @BeforeEach
    void setUp() {
        transformer = new BookingToBookingDTOTransformer();
    }

    @Test
    void shouldTransformBookingToDTO() {
        Booking booking = Booking.builder()
                .id(1L)
                .unit(Unit.builder().id(2L).available(true).build())
                .user(User.builder().id(3L).username("testuser").email("test@example.com").password("password").build())
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusDays(2))
                .paid(true)
                .build();

        BookingDTO result = transformer.transform(booking);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(2L, result.getUnitId());
        assertEquals(3L, result.getUserId());
    }
}
