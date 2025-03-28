package com.example.booking.model.specification.unit;

import com.example.booking.model.specification.FilterContext;
import com.example.booking.model.specification.FilterValueRequestScope;
import com.example.booking.model.Unit;
import com.example.booking.model.specification.AbstractFilterResolver;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UnitFilterResolver extends AbstractFilterResolver<Unit> {
    protected UnitFilterResolver(List<? extends Specification<Unit>> filters, FilterValueRequestScope filterValueRequestScope) {
        super(FilterContext.UNIT, filters, filterValueRequestScope);
    }
}
