package com.example.booking.model.specification;

import com.example.booking.service.FilterResolver;
import org.springframework.data.jpa.domain.Specification;

import java.util.*;

public abstract class AbstractFilterResolver<T> implements FilterResolver<T> {

    private final FilterContext filterContext;
    private final List<? extends Specification<T>> filters;
    private final Map<String, Specification<T>> specificationMap = new HashMap<>();
    private final FilterValueRequestScope filterValueRequestScope;

    protected AbstractFilterResolver(FilterContext filterContext,
                                     List<? extends Specification<T>> filters,
                                     FilterValueRequestScope filterValueRequestScope) {
        this.filterContext = filterContext;
        this.filters = filters;
        this.filterValueRequestScope = filterValueRequestScope;
    }

    @Override
    public Specification<T> resolve(String filterKey, Object filterValue) {
        if (!specificationMap.containsKey(filterKey)) {
            filters.stream()
                    .filter(filter -> filter.getClass().isAnnotationPresent(Filter.class))
                    .filter(filter -> {
                        Filter annotation = filter.getClass().getAnnotation(Filter.class);
                        return annotation.context().equals(filterContext) &&
                                Arrays.asList(annotation.key().split(",")).contains(filterKey);
                    })
                    .findFirst()
                    .ifPresent(specification -> specificationMap.put(filterKey, specification));
        }
        return specificationMap.get(filterKey);
    }

    @Override
    public Specification<T> resolveAll(Map<String, Object> filters) {
        return this.resolveAll(filters, null);
    }

    @Override
    public Specification<T> resolveAll(Map<String, Object> filters, Specification<T> rootSpec) {
        for (Map.Entry<String, Object> filter : filters.entrySet()) {
            Specification<T> specification = this.resolve(filter.getKey(), filter.getValue());
            rootSpec = Optional.ofNullable(rootSpec)
                    .map(spec -> spec.and(specification))
                    .orElseGet(() -> Specification.where(specification));
        }
        filterValueRequestScope.setFilterValue(filters);
        return rootSpec;
    }
}
