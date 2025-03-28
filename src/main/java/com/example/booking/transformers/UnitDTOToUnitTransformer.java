package com.example.booking.transformers;


import com.example.booking.dto.UnitDTO;
import com.example.booking.model.AccommodationType;
import com.example.booking.model.Unit;
import com.example.booking.model.User;
import com.example.booking.service.UserServiceEntity;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.Transformer;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class UnitDTOToUnitTransformer implements Transformer<UnitDTO, Unit> {
    private final UserServiceEntity userServiceEntity;

    @Override
    public Unit transform(UnitDTO unitDTO) {
        User authorId = userServiceEntity.getUserById(unitDTO.getAuthorId()).orElseThrow(() -> new RuntimeException("Can`t get user by id."));
        return Unit.builder()
                .floor(unitDTO.getFloor())
                .rooms(unitDTO.getRooms())
                .cost(unitDTO.getCost())
                .markup(unitDTO.getCost().multiply(BigDecimal.valueOf(0.15)))
                .author(authorId)
                .available(unitDTO.isAvailable())
                .description(unitDTO.getDescription())
                .type(AccommodationType.valueOf(unitDTO.getType()))
                .build();
    }
}
