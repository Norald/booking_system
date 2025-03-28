package com.example.booking.service;

import com.example.booking.model.Event;
import com.example.booking.repository.EventRepository;
import com.example.booking.service.impl.EventServiceEntityImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class EventServiceEntityTest {
    @InjectMocks
    private EventServiceEntityImpl eventServiceEntity;

    @Mock
    private EventRepository eventRepository;

    @Test
    void shouldAddEvent() {
        Event event = new Event();
        eventServiceEntity.addEvent(event);
        Mockito.verify(eventRepository, Mockito.times(1)).save(event);
    }
}
