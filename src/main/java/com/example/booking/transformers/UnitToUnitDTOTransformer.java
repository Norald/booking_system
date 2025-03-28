package com.example.booking.transformers;

import com.example.booking.dto.UnitDTO;
import com.example.booking.model.Unit;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.Transformer;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UnitToUnitDTOTransformer implements Transformer<Unit, UnitDTO> {
    @Override
    public UnitDTO transform(Unit unit) {
        return UnitDTO.builder()
                .id(unit.getId())
                .rooms(unit.getRooms())
                .type(unit.getType().name())
                .floor(unit.getFloor())
                .available(unit.isAvailable())
                .cost(unit.getCost().add(unit.getMarkup()))
                .description(unit.getDescription())
                .authorId(unit.getAuthor().getId())
                .build();
    }
}
