package com.example.booking.model.specification.booking;

import com.example.booking.model.Booking;
import com.example.booking.model.specification.AbstractFilterResolver;
import com.example.booking.model.specification.FilterContext;
import com.example.booking.model.specification.FilterValueRequestScope;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookingFilterResolver extends AbstractFilterResolver<Booking> {
    protected BookingFilterResolver(List<? extends Specification<Booking>> filters, FilterValueRequestScope filterValueRequestScope) {
        super(FilterContext.BOOKING, filters, filterValueRequestScope);
    }
}
