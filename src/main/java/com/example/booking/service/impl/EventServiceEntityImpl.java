package com.example.booking.service.impl;

import com.example.booking.model.Event;
import com.example.booking.repository.EventRepository;
import com.example.booking.service.EventServiceEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventServiceEntityImpl implements EventServiceEntity {
    private final EventRepository eventRepository;

    @Override
    public void addEvent(Event event) {
        eventRepository.save(event);
    }
}
