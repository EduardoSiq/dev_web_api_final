package com.dac.api.app.repository;

import com.dac.api.app.model.Activity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ActivityRepository extends JpaRepository<Activity, Long> {
    @EntityGraph(attributePaths = { "favoritedByUsers", "edition", "space" })
    List<Activity> findByDateAndStartTimeBetween(
            LocalDate date,
            LocalTime startTime,
            LocalTime endTime);

}
