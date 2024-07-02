package com.dac.api.app.service;

import com.dac.api.app.dto.EditionSaveDTO;
import com.dac.api.app.model.Edition;

import java.util.Optional;

public interface EditionService {
    Optional<Edition> findById(Long id);
    Edition update(Long id, EditionSaveDTO data);
    void deleteById(Long id);
    Edition updateOrganizer(Long id, Long organizer_id);
}
