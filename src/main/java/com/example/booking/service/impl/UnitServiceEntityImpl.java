package com.example.booking.service.impl;

import com.example.booking.model.Unit;
import com.example.booking.repository.UnitRepository;
import com.example.booking.service.UnitServiceEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UnitServiceEntityImpl implements UnitServiceEntity {

    private final UnitRepository unitRepository;

    @Override
    public Page<Unit> findAll(Specification<Unit> rootSpec, Pageable pageable) {
        return unitRepository.findAll(rootSpec, pageable);
    }

    @Override
    public Optional<Unit> findById(Long id) {
        return unitRepository.findById(id);
    }

    @Override
    public void save(Unit unit) {
        unitRepository.save(unit);
    }

    @Override
    public long countByAvailableTrue(){
        return unitRepository.countByAvailableTrue();
    }
}
