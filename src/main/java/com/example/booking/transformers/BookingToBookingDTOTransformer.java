package com.example.booking.transformers;

import com.example.booking.dto.BookingDTO;
import com.example.booking.model.Booking;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.Transformer;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookingToBookingDTOTransformer implements Transformer<Booking, BookingDTO> {
    @Override
    public BookingDTO transform(Booking booking) {
        return BookingDTO.builder()
                .id(booking.getId())
                .unitId(booking.getUnit().getId())
                .userId(booking.getUser().getId())
                .startDate(booking.getStartDate())
                .endDate(booking.getEndDate())
                .createdAt(booking.getCreatedAt())
                .paid(booking.isPaid())
                .build();

    }
}
