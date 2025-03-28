package com.example.booking.model.specification.unit;

import com.example.booking.model.specification.Filter;
import com.example.booking.model.specification.FilterContext;
import com.example.booking.model.specification.FilterValueRequestScope;
import com.example.booking.model.Unit;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Filter(key = UnitFilters.COST_TO, context = FilterContext.UNIT)
public class UnitCostToSpecification implements Specification<Unit> {

    private final FilterValueRequestScope filterValueRequestScope;

    public UnitCostToSpecification(FilterValueRequestScope filterValueRequestScope) {
        this.filterValueRequestScope = filterValueRequestScope;
    }

    @Override
    public Predicate toPredicate(Root<Unit> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        String costTo = (String) filterValueRequestScope.getValue(key());
        Expression<BigDecimal> finalPrice = criteriaBuilder.sum(root.get("cost"), root.get("markup"));
        return costTo == null ? criteriaBuilder.conjunction() : criteriaBuilder.lessThanOrEqualTo(finalPrice, new BigDecimal(costTo));
    }

    private String key() {
        return getClass().getAnnotation(Filter.class).key();
    }
}
