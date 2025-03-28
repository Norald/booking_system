package com.example.booking.transformers;

import com.example.booking.dto.UnitDTO;
import com.example.booking.model.AccommodationType;
import com.example.booking.model.Unit;
import com.example.booking.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UnitToUnitDTOTransformerTest {

    private UnitToUnitDTOTransformer transformer;

    @BeforeEach
    void setUp() {
        transformer = new UnitToUnitDTOTransformer();
    }

    @Test
    void shouldTransformUnitToDTO() {
        Unit unit = Unit.builder()
                .id(1L)
                .type(AccommodationType.FLAT)
                .cost(new BigDecimal(3000.0))
                .markup(new BigDecimal(1000.0))
                .author(User.builder().id(1L).build())
                .available(true).build();

        UnitDTO result = transformer.transform(unit);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertTrue(result.isAvailable());
    }
}
