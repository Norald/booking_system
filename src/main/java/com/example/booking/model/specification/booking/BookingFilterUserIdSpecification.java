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
@Filter(key = BookingFilters.USER_ID, context = FilterContext.BOOKING)
public class BookingFilterUserIdSpecification implements Specification<Booking> {

    private final FilterValueRequestScope filterValueRequestScope;

    public BookingFilterUserIdSpecification(FilterValueRequestScope filterValueRequestScope) {
        this.filterValueRequestScope = filterValueRequestScope;
    }

    @Override
    public Predicate toPredicate(Root<Booking> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Object userObj = filterValueRequestScope.getValue(key());

        if (userObj == null) {
            return criteriaBuilder.conjunction();
        }

        Long userId = Long.parseLong(userObj.toString());

        return criteriaBuilder.equal(root.get("user_id"), userId);
    }

    private String key() {
        return getClass().getAnnotation(Filter.class).key();
    }
}
