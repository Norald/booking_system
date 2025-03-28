package com.example.booking.service;

import com.example.booking.model.Unit;
import org.springframework.data.jpa.domain.Specification;

import java.util.Map;

public interface FilterResolver<T> {
    Specification<T> resolve(String key, Object value);
    Specification<T> resolveAll(Map<String, Object> filters);
    Specification<T> resolveAll(Map<String, Object> filters, Specification<T> rootSpec);
}
