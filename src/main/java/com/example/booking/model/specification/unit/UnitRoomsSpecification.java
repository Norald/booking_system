package com.example.booking.model.specification.unit;

import com.example.booking.model.specification.Filter;
import com.example.booking.model.specification.FilterContext;
import com.example.booking.model.specification.FilterValueRequestScope;
import com.example.booking.model.Unit;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
@Filter(key = UnitFilters.ROOMS, context = FilterContext.UNIT)
public class UnitRoomsSpecification implements Specification<Unit> {

    private final FilterValueRequestScope filterValueRequestScope;

    public UnitRoomsSpecification(FilterValueRequestScope filterValueRequestScope) {
        this.filterValueRequestScope = filterValueRequestScope;
    }

    @Override
    public Predicate toPredicate(Root<Unit> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        String rooms = (String) filterValueRequestScope.getValue(key());
        return rooms == null ? criteriaBuilder.conjunction() : criteriaBuilder.equal(root.get("rooms"), Integer.valueOf(rooms));
    }

    private String key() {
        return getClass().getAnnotation(Filter.class).key();
    }
}
