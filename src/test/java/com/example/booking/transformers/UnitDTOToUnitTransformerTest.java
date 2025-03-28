package com.example.booking.transformers;

import com.example.booking.dto.UnitDTO;
import com.example.booking.model.Unit;
import com.example.booking.model.User;
import com.example.booking.service.UserServiceEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UnitDTOToUnitTransformerTest {

    @InjectMocks
    private UnitDTOToUnitTransformer transformer;

    @Mock
    private UserServiceEntity userServiceEntity;

    @Test
    void shouldTransformUnitDTOToUnitSuccessfully() {
        Long authorId = 1L;
        UnitDTO unitDTO = new UnitDTO();
        unitDTO.setFloor(3);
        unitDTO.setRooms(2);
        unitDTO.setCost(new BigDecimal("1000.00"));
        unitDTO.setAuthorId(authorId);
        unitDTO.setAvailable(true);
        unitDTO.setDescription("Nice apartment");
        unitDTO.setType("FLAT");

        User mockUser = new User();
        mockUser.setId(authorId);
        mockUser.setUsername("Test User");

        when(userServiceEntity.getUserById(authorId)).thenReturn(Optional.of(mockUser));

        Unit result = transformer.transform(unitDTO);

        assertNotNull(result);
        assertEquals(unitDTO.getFloor(), result.getFloor());
        assertEquals(unitDTO.getRooms(), result.getRooms());
        assertEquals(unitDTO.getCost(), result.getCost());
        assertEquals(unitDTO.getCost().multiply(BigDecimal.valueOf(0.15)), result.getMarkup());
        assertEquals(mockUser, result.getAuthor());
        assertEquals(unitDTO.isAvailable(), result.isAvailable());
        assertEquals(unitDTO.getDescription(), result.getDescription());

        verify(userServiceEntity, times(1)).getUserById(authorId);
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {
        Long authorId = 1L;
        UnitDTO unitDTO = new UnitDTO();
        unitDTO.setAuthorId(authorId);

        when(userServiceEntity.getUserById(authorId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> transformer.transform(unitDTO));

        assertEquals("Can`t get user by id.", exception.getMessage());
        verify(userServiceEntity, times(1)).getUserById(authorId);
    }
}
