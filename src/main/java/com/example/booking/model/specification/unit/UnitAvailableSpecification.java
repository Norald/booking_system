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
@Filter(key = UnitFilters.AVAILABLE, context = FilterContext.UNIT)
public class UnitAvailableSpecification implements Specification<Unit> {

    private final FilterValueRequestScope filterValueRequestScope;

    public UnitAvailableSpecification(FilterValueRequestScope filterValueRequestScope) {
        this.filterValueRequestScope = filterValueRequestScope;
    }

    @Override
    public Predicate toPredicate(Root<Unit> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Object availableValue = filterValueRequestScope.getValue(key());

        if (availableValue == null) {
            return criteriaBuilder.conjunction();
        }

        Boolean available = Boolean.parseBoolean(availableValue.toString());

        return criteriaBuilder.equal(root.get("available"), available);
    }

    private String key() {
        return getClass().getAnnotation(Filter.class).key();
    }

}
