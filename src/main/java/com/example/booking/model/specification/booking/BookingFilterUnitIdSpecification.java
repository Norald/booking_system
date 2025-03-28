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
@Filter(key = BookingFilters.UNIT_ID, context = FilterContext.BOOKING)
public class BookingFilterUnitIdSpecification implements Specification<Booking> {

    private final FilterValueRequestScope filterValueRequestScope;

    public BookingFilterUnitIdSpecification(FilterValueRequestScope filterValueRequestScope) {
        this.filterValueRequestScope = filterValueRequestScope;
    }

    @Override
    public Predicate toPredicate(Root<Booking> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Object unitObj = filterValueRequestScope.getValue(key());

        if (unitObj == null) {
            return criteriaBuilder.conjunction();
        }

        Long unitId = Long.parseLong(unitObj.toString());

        return criteriaBuilder.equal(root.get("unit_id"), unitId);
    }

    private String key() {
        return getClass().getAnnotation(Filter.class).key();
    }
}
