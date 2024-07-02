package com.dac.api.app.controller;

import com.dac.api.app.dto.ActivityResponseDTO;
import com.dac.api.app.dto.ActivitySaveDTO;
import com.dac.api.app.dto.ActivityShowResponseDTO;
import com.dac.api.app.dto.ApiResponseDTO;
import com.dac.api.app.model.Activity;
import com.dac.api.app.service.impl.ActivityServiceImpl;
import com.dac.api.app.util.GenericMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Tag(name = "Activity endpoints")
@RestController
@RequestMapping("/api/activities")
@RequiredArgsConstructor
public class ActivityController {
    private final ActivityServiceImpl activityService;
    private final GenericMapper genericMapper;

    @GetMapping("/")
    public ResponseEntity<ApiResponseDTO> index() {
        try {
            final List<Activity> activities = this.activityService.findAll();
            final List<ActivityResponseDTO> activitiesResponse = activities.stream()
                    .map(activity -> this.genericMapper.toDTO(activity, ActivityResponseDTO.class))
                    .collect(Collectors.toList());
            return ResponseEntity.ok(new ApiResponseDTO("List of activities", activitiesResponse));
        } catch (final Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponseDTO(e.getMessage(), null));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO> show(@PathVariable final Long id) {
        try {
            final Optional<Activity> activity = this.activityService.findById(id);
            final ActivityShowResponseDTO response = this.genericMapper.toDTO(activity, ActivityShowResponseDTO.class);
            return ResponseEntity.ok(new ApiResponseDTO("Show activity", response));
        } catch (final Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponseDTO(e.getMessage(), null));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDTO> delete(@PathVariable final Long id) {
        try {
            this.activityService.deleteById(id);
            return ResponseEntity.ok(new ApiResponseDTO("Activity deleted", null));
        } catch (final Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponseDTO(e.getMessage(), null));
        }
    }

    @PostMapping("/")
    public ResponseEntity<ApiResponseDTO> create(@Valid @RequestBody final ActivitySaveDTO entity) {
        try {
            final Activity activity = this.activityService.save(entity);
            final ActivityShowResponseDTO response = this.genericMapper.toDTO(activity, ActivityShowResponseDTO.class);
            return ResponseEntity.ok(new ApiResponseDTO("Activity created", response));
        } catch (final Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponseDTO(e.getMessage(), null));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDTO> update(@PathVariable final Long id, @Valid @RequestBody final ActivitySaveDTO entity) {
        try {
            final Activity activity = this.activityService.update(id, entity);
            final ActivityShowResponseDTO response = this.genericMapper.toDTO(activity, ActivityShowResponseDTO.class);
            return ResponseEntity.ok(new ApiResponseDTO("Activity updated", response));
        } catch (final Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponseDTO(e.getMessage(), null));
        }
    }
}
