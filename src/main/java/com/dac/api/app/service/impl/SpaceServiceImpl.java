package com.dac.api.app.service.impl;

import com.dac.api.app.dto.SpaceSaveDTO;
import com.dac.api.app.exception.SpaceNotFoundException;
import com.dac.api.app.model.Space;
import com.dac.api.app.repository.EditionRepository;
import com.dac.api.app.repository.SpaceRepository;
import com.dac.api.app.service.SpaceService;
import com.dac.api.app.util.GenericMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SpaceServiceImpl implements SpaceService {

    private final SpaceRepository spaceRepository;
    private final EditionRepository editionRepository;
    private final GenericMapper genericMapper;

    @Override
    public List<Space> findAll() {
        return this.spaceRepository.findAll();
    }

    @Override
    public Space save(final Long id, final SpaceSaveDTO data) {
        final Space space = genericMapper.toEntity(data, Space.class);

        return this.spaceRepository.save(space);
    }

    public Optional<Space> findById(final Long id) {
        final Space space = this.spaceRepository.findById(id).orElseThrow(SpaceNotFoundException::new);
        return Optional.of(space);
    }

    @Override
    public Space update(final Long id, final Long editionId, final SpaceSaveDTO data) {
        final Space space = this.spaceRepository.findById(id).orElseThrow(SpaceNotFoundException::new);

        space.setCapacity(data.getCapacity());
        if (data.getName() != null)
            space.setName(data.getName());
        if (data.getLocation() != null)
            space.setLocation(data.getLocation());
        if (data.getResources() != null)
            space.setResources(data.getResources());

        return this.spaceRepository.save(space);
    }

    @Override
    public void deleteById(final Long id, final Long editionId) {
        this.spaceRepository.findById(id).orElseThrow(SpaceNotFoundException::new);
        this.spaceRepository.deleteById(id);
    }
}
