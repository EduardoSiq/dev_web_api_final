package com.dac.api.app.service;

import com.dac.api.app.dto.SpaceSaveDTO;
import com.dac.api.app.model.Space;

import java.util.List;
import java.util.Optional;

public interface SpaceService {
    List<Space> findAll();
    Space save(Long id, SpaceSaveDTO data);
    Optional<Space> findById(Long id);
    Space update(Long id, Long editionId, SpaceSaveDTO data);
    void deleteById(Long id, Long editionId);
}
