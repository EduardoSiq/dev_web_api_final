package com.dac.api.app.service;

import com.dac.api.app.dto.EventSaveDTO;
import com.dac.api.app.model.Event;

import java.util.List;
import java.util.Optional;

public interface EventService {

    List<Event> findAll();
    Event save(EventSaveDTO dto);
    Optional<Event> findById(Long id);
    void deleteById(Long id);
    Event update(Long id, EventSaveDTO data);
}
