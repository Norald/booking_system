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

@Component
@Filter(key = BookingFilters.PAID, context = FilterContext.BOOKING)
public class BookingPaidSpecification implements Specification<Booking> {

    private final FilterValueRequestScope filterValueRequestScope;

    public BookingPaidSpecification(FilterValueRequestScope filterValueRequestScope) {
        this.filterValueRequestScope = filterValueRequestScope;
    }

    @Override
    public Predicate toPredicate(Root<Booking> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Object paidObj = filterValueRequestScope.getValue(key());

        if (paidObj == null) {
            return criteriaBuilder.conjunction();
        }

        Boolean paid = Boolean.parseBoolean(paidObj.toString());

        return criteriaBuilder.equal(root.get("paid"), paid);
    }

    private String key() {
        return getClass().getAnnotation(Filter.class).key();
    }
}
