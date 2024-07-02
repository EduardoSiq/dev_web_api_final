package com.dac.api.app.service.impl;

import com.dac.api.app.dto.EditionSaveDTO;
import com.dac.api.app.exception.EditionNotFoundException;
import com.dac.api.app.exception.EventNotFoundException;
import com.dac.api.app.exception.UserNotFoundException;
import com.dac.api.app.model.Edition;
import com.dac.api.app.model.Event;
import com.dac.api.app.model.User;
import com.dac.api.app.repository.EditionRepository;
import com.dac.api.app.repository.EventRepository;
import com.dac.api.app.repository.UserRepository;
import com.dac.api.app.service.EditionService;
import com.dac.api.app.util.GenericMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EditionServiceImpl implements EditionService{

    private final EditionRepository editionRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final GenericMapper genericMapper;

    public List<Edition> findAll() {
        return this.editionRepository.findAll();
    }

    public Edition save(final EditionSaveDTO data) {
        final Edition edition = genericMapper.toEntity(data, Edition.class);
        final Event event = this.eventRepository.findById(data.getEvent_id()).orElseThrow(EventNotFoundException::new);
        edition.setEvent(event);

        return editionRepository.save(edition);
    }

    @Override
    public Optional<Edition> findById(final Long id) {
        final Edition edition = this.editionRepository.findById(id).orElseThrow(EditionNotFoundException::new);

        return Optional.of(edition);
    }

    @Override
    public Edition update(final Long id, final EditionSaveDTO data) {
        final Edition edition = this.editionRepository.findById(id).orElseThrow(EditionNotFoundException::new);

        this.eventRepository.findById(data.getEvent_id()).orElseThrow(EventNotFoundException::new);
        return this.editionRepository.save(edition);
    }

    @Override
    public void deleteById(final Long id) {
        this.editionRepository.findById(id).orElseThrow(EditionNotFoundException::new);
        this.editionRepository.deleteById(id);
    }

    public Edition updateOrganizer(final Long id, final Long organizer_id) {
        final Edition edition = this.editionRepository.findById(id).orElseThrow(EditionNotFoundException::new);
        final User organizer = this.userRepository.findById(organizer_id).orElseThrow(UserNotFoundException::new);

        edition.setOrganizer(organizer);

        return this.editionRepository.save(edition);
    }
}
