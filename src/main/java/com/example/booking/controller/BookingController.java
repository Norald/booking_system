package com.example.booking.controller;

import com.example.booking.dto.BookingDTO;
import com.example.booking.service.BookingService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    @PostMapping("/create")
    public ResponseEntity<BookingDTO> bookUnit(@RequestBody BookingDTO bookingDTO) {
        return ResponseEntity.ok(bookingService.bookUnit(bookingDTO.getUnitId(), bookingDTO.getUserId(), bookingDTO.getStartDate(), bookingDTO.getEndDate()));
    }

    @DeleteMapping("/{bookingId}")
    public ResponseEntity<Void> cancelBooking(@PathVariable Long bookingId) {
        bookingService.cancelBooking(bookingId);
        return ResponseEntity.ok().build();
    }

    @Parameters({
            @Parameter(name = "unitId", description = "unitId parameter", in = ParameterIn.QUERY),
            @Parameter(name = "userId", description = "userId parameter", in = ParameterIn.QUERY),
            @Parameter(name = "paid", description = "paid parameter", in = ParameterIn.QUERY),
            @Parameter(name = "createdAtFrom", description = "createdAtFrom parameter", in = ParameterIn.QUERY),
            @Parameter(name = "createdAtTo", description = "createdAtTo parameter", in = ParameterIn.QUERY)
    })
    @GetMapping
    public ResponseEntity<Page<BookingDTO>> getBookings(@RequestParam Map<String, Object> allParams,
                                                        @PageableDefault(page = 0, size = 10, sort = "createdAt", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(bookingService.findAll(allParams, pageable));
    }
}