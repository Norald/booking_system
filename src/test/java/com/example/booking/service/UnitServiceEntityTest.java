package com.example.booking.service;

import com.example.booking.model.Unit;
import com.example.booking.repository.UnitRepository;
import com.example.booking.service.impl.UnitServiceEntityImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UnitServiceEntityTest {

    @InjectMocks
    private UnitServiceEntityImpl unitServiceEntity;

    @Mock
    private UnitRepository unitRepository;

    @Mock
    private Specification<Unit> specification;

    @Mock
    private Pageable pageable;

    @Test
    void shouldFindAllUnits() {
        List<Unit> units = List.of(new Unit(), new Unit());
        Page<Unit> page = new PageImpl<>(units);

        Mockito.when(unitRepository.findAll(specification, pageable)).thenReturn(page);

        Page<Unit> result = unitServiceEntity.findAll(specification, pageable);

        assertNotNull(result);
        assertEquals(2, result.getContent().size());
    }

    @Test
    void shouldFindUnitById() {
        Long id = 1L;
        Unit unit = new Unit();
        Mockito.when(unitRepository.findById(id)).thenReturn(Optional.of(unit));

        Optional<Unit> result = unitServiceEntity.findById(id);

        assertTrue(result.isPresent());
        assertEquals(unit, result.get());
    }

    @Test
    void shouldSaveUnit() {
        Unit unit = new Unit();
        unitServiceEntity.save(unit);
        Mockito.verify(unitRepository, Mockito.times(1)).save(unit);
    }

    @Test
    void shouldCountAvailableUnits() {
        Mockito.when(unitRepository.countByAvailableTrue()).thenReturn(10L);
        long count = unitServiceEntity.countByAvailableTrue();
        assertEquals(10L, count);
    }
}
