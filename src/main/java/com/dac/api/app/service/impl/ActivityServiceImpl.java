package com.dac.api.app.service.impl;

import com.dac.api.app.dto.ActivitySaveDTO;
import com.dac.api.app.exception.ActivityNotFoundException;
import com.dac.api.app.exception.EditionNotFoundException;
import com.dac.api.app.exception.SpaceNotFoundException;
import com.dac.api.app.model.Activity;
import com.dac.api.app.model.Edition;
import com.dac.api.app.model.Space;
import com.dac.api.app.repository.ActivityRepository;
import com.dac.api.app.repository.EditionRepository;
import com.dac.api.app.repository.SpaceRepository;
import com.dac.api.app.service.ActivityService;
import com.dac.api.app.util.GenericMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ActivityServiceImpl implements ActivityService {

    private final ActivityRepository activityRepository;
    private final SpaceRepository spaceRepository;
    private final EditionRepository editionRepository;
    private final GenericMapper genericMapper;

    @Override
    public List<Activity> findAll() {
        return this.activityRepository.findAll();
    }

    @Override
    public Activity save(final ActivitySaveDTO data) {
        final Edition edition = this.editionRepository.findById(data.getEdition_id()).orElseThrow(EditionNotFoundException::new);


        final Space space = this.spaceRepository.findById(data.getSpace_id()).orElseThrow(SpaceNotFoundException::new);

        final Activity activity = genericMapper.toEntity(data, Activity.class);

        activity.setEdition(edition);
        activity.setSpace(space);

        return this.activityRepository.save(activity);
    }

    @Override
    public Optional<Activity> findById(final Long id) {
        final Activity activity = this.activityRepository.findById(id).orElseThrow(ActivityNotFoundException::new);
        return Optional.of(activity);
    }

    @Override
    public Activity update(final Long id, final ActivitySaveDTO data) {
        final Activity activity = this.activityRepository.findById(id).orElseThrow(ActivityNotFoundException::new);

        final Edition edition = this.editionRepository.findById(data.getEdition_id()).orElseThrow(EditionNotFoundException::new);

        final Space space = this.spaceRepository.findById(data.getSpace_id()).orElseThrow(SpaceNotFoundException::new);

        activity.setType(data.getType());
        activity.setName(data.getName());
        activity.setDescription(data.getDescription());
        activity.setDate(data.getDate());
        activity.setStartTime(data.getStartTime());
        activity.setEndTime(data.getEndTime());
        activity.setEdition(edition);
        activity.setSpace(space);

        return this.activityRepository.save(activity);
    }

    @Override
    public void deleteById(final Long id) {
        this.activityRepository.deleteById(id);
    }

    public List<Activity> getActivitiesStartingWithinNextHour() {
        final LocalDateTime now = LocalDateTime.now();

        final LocalDateTime oneHourLater = now.plusHours(1);

        return activityRepository.findByDateAndStartTimeBetween(now.toLocalDate(),
                now.toLocalTime(), oneHourLater.toLocalTime());
    }

    public Activity setMailSent(final Activity data) {
        return this.activityRepository.save(data);
    }
}
