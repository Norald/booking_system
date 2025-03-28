package com.example.booking.service;

import com.example.booking.model.Unit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;

public interface UnitServiceEntity {
    Page<Unit> findAll(Specification<Unit> rootSpec, Pageable pageable);

    Optional<Unit> findById(Long id);

    void save(Unit unit);

    long countByAvailableTrue();
}
