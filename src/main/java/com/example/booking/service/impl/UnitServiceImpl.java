package com.example.booking.service.impl;

import com.example.booking.dto.UnitDTO;
import com.example.booking.model.AccommodationType;
import com.example.booking.model.Unit;
import com.example.booking.model.specification.unit.UnitFilterResolver;
import com.example.booking.service.UnitService;
import com.example.booking.service.UnitServiceEntity;
import com.example.booking.transformers.UnitDTOToUnitTransformer;
import com.example.booking.transformers.UnitToUnitDTOTransformer;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class UnitServiceImpl implements UnitService {

    private final UnitFilterResolver filterResolver;
    private final UnitServiceEntity userServiceEntity;
    private final UnitToUnitDTOTransformer unitToUnitDTOTransformer;
    private final UnitDTOToUnitTransformer unitDTOToUnitTransformer;

    @Override
    public Page<UnitDTO> findAll(Map<String, Object> filters, Pageable pageable) {
        Specification<Unit> rootSpec = filterResolver.resolveAll(filters);
        Page<Unit> unitEntities = userServiceEntity.findAll(rootSpec, pageable);

        List<UnitDTO> events = unitEntities.getContent().stream()
                .map(unitToUnitDTOTransformer::transform)
                .collect(Collectors.toList());

        return new PageImpl<>(events, pageable, unitEntities.getTotalElements());
    }

    @Override
    public void createUnit(UnitDTO unitDTO) {
        userServiceEntity.save(unitDTOToUnitTransformer.transform(unitDTO));
    }
}
