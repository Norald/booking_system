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
@Filter(key = UnitFilters.FLOOR, context = FilterContext.UNIT)
public class UnitFloorSpecification implements Specification<Unit> {

    private final FilterValueRequestScope filterValueRequestScope;

    public UnitFloorSpecification(FilterValueRequestScope filterValueRequestScope) {
        this.filterValueRequestScope = filterValueRequestScope;
    }

    @Override
    public Predicate toPredicate(Root<Unit> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        String floor = (String) filterValueRequestScope.getValue(key());
        return floor == null ? criteriaBuilder.conjunction() : criteriaBuilder.equal(root.get("floor"), Integer.valueOf(floor));
    }

    private String key() {
        return getClass().getAnnotation(Filter.class).key();
    }
}
