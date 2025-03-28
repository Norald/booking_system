package com.example.booking.service;

import com.example.booking.dto.UnitDTO;
import com.example.booking.model.Unit;
import com.example.booking.model.specification.unit.UnitFilterResolver;
import com.example.booking.service.impl.UnitServiceImpl;
import com.example.booking.transformers.UnitDTOToUnitTransformer;
import com.example.booking.transformers.UnitToUnitDTOTransformer;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class UnitServiceTest {

    @InjectMocks
    private UnitServiceImpl unitService;

    @Mock
    private UnitFilterResolver filterResolver;

    @Mock
    private UnitServiceEntity userServiceEntity;

    @Mock
    private UnitToUnitDTOTransformer unitToUnitDTOTransformer;

    @Mock
    private UnitDTOToUnitTransformer unitDTOToUnitTransformer;

    @Mock
    private Pageable pageable;

    @Mock
    private Specification<Unit> specification;

    @Test
    void shouldFindAllUnits() {
        Map<String, Object> filters = new HashMap<>();
        List<Unit> units = List.of(new Unit(), new Unit());
        Page<Unit> unitPage = new PageImpl<>(units);

        Mockito.when(filterResolver.resolveAll(filters)).thenReturn(specification);
        Mockito.when(userServiceEntity.findAll(specification, pageable)).thenReturn(unitPage);
        Mockito.when(unitToUnitDTOTransformer.transform(Mockito.any())).thenReturn(new UnitDTO());

        Page<UnitDTO> result = unitService.findAll(filters, pageable);

        assertNotNull(result);
        assertEquals(2, result.getContent().size());
    }

    @Test
    void shouldCreateUnit() {
        UnitDTO unitDTO = new UnitDTO();
        Unit unit = new Unit();

        Mockito.when(unitDTOToUnitTransformer.transform(unitDTO)).thenReturn(unit);

        unitService.createUnit(unitDTO);

        Mockito.verify(userServiceEntity, Mockito.times(1)).save(unit);
    }
}
