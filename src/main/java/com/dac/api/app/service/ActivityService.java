package com.dac.api.app.service;

import com.dac.api.app.dto.ActivitySaveDTO;
import com.dac.api.app.model.Activity;

import java.util.List;
import java.util.Optional;

public interface ActivityService {

    List<Activity> findAll();

    Activity save(ActivitySaveDTO data);

    Optional<Activity> findById(Long id);

    Activity update(Long id, ActivitySaveDTO data);

    void deleteById(Long id);

    List<Activity> getActivitiesStartingWithinNextHour();

    Activity setMailSent(Activity data);
}
