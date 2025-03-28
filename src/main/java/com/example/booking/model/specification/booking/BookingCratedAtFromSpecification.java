package com.example.booking.model.specification.booking;


import com.example.booking.model.Booking;
import com.example.booking.model.specification.Filter;
import com.example.booking.model.specification.FilterContext;
import com.example.booking.model.specification.FilterValueRequestScope;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@Filter(key = BookingFilters.CREATED_AT_FROM, context = FilterContext.BOOKING)
public class BookingCratedAtFromSpecification implements Specification<Booking> {

    public static final String DATE_FORMAT = "dd.MM.yyyy";
    public static final DateTimeFormatter DATE = DateTimeFormatter.ofPattern(DATE_FORMAT);

    private final FilterValueRequestScope filterValueRequestScope;

    public BookingCratedAtFromSpecification(FilterValueRequestScope filterValueRequestScope) {
        this.filterValueRequestScope = filterValueRequestScope;
    }

    @Override
    public Predicate toPredicate(Root<Booking> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        String value = (String) filterValueRequestScope.getValue(key());
        LocalDateTime date = this.parseToLocalDateTime(value);

        return criteriaBuilder.greaterThanOrEqualTo(root.get("createdAtFrom"), date);
    }


    private LocalDateTime parseToLocalDateTime(String value) {
        return LocalDate.parse(value, DATE).atStartOfDay();
    }

    private String key() {
        return getClass().getAnnotation(Filter.class).key();
    }
}
