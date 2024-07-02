package com.dac.api.app.service.impl;

import com.dac.api.app.dto.EventSaveDTO;
import com.dac.api.app.exception.EventNotFoundException;
import com.dac.api.app.model.Event;
import com.dac.api.app.repository.EventRepository;
import com.dac.api.app.service.EventService;
import com.dac.api.app.util.GenericMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final GenericMapper genericMapper;

    @Override
    public List<Event> findAll() {
        return this.eventRepository.findAll();
    }

    public Event save(final EventSaveDTO dto) {
        final Event event = genericMapper.toEntity(dto, Event.class);
        return eventRepository.save(event);
    }

    @Override
    public Optional<Event> findById(final Long id) {
        final Event event = this.eventRepository.getReferenceById(id);
        return Optional.of(event);
    }

    @Override
    public void deleteById(final Long id) {
        this.eventRepository.deleteById(id);
    }

    @Override
    public Event update(final Long id, final EventSaveDTO data) {
        final Event event = this.eventRepository.findById(id).orElseThrow(EventNotFoundException::new);

        event.setName(data.getName());
        event.setDescription(data.getDescription());
        event.setAcronym(data.getAcronym());
        event.setUrl(data.getUrl());
        return this.eventRepository.save(event);
    }
}
