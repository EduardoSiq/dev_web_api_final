package com.dac.api.app.controller;

import com.dac.api.app.dto.ApiResponseDTO;
import com.dac.api.app.dto.EventResponseDTO;
import com.dac.api.app.dto.EventSaveDTO;
import com.dac.api.app.dto.EventShowResponseDTO;
import com.dac.api.app.model.Event;
import com.dac.api.app.service.impl.EventServiceImpl;
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

@Tag(name = "Event endpoints")
@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventController {
    private final EventServiceImpl eventService;
    private final GenericMapper genericMapper;

    @GetMapping("/")
    public ResponseEntity<ApiResponseDTO> index() {
        try {
            final List<Event> events = this.eventService.findAll();
            final List<EventResponseDTO> response = events.stream()
                    .map(event -> this.genericMapper.toDTO(event, EventResponseDTO.class))
                    .collect(Collectors.toList());
            return ResponseEntity.ok(new ApiResponseDTO("List of events", response));
        } catch (final Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponseDTO(e.getMessage(), null));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO> show(@PathVariable final Long id) {
        try {
            final Optional<Event> event = this.eventService.findById(id);

            final EventShowResponseDTO response = this.genericMapper.toDTO(event, EventShowResponseDTO.class);
            return ResponseEntity.ok(new ApiResponseDTO("Show event", response));
        } catch (final Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponseDTO(e.getMessage(), null));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDTO> delete(@PathVariable final Long id) {
        try {
            this.eventService.deleteById(id);
            return ResponseEntity.ok(new ApiResponseDTO("Event deleted", null));
        } catch (final Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponseDTO(e.getMessage(), null));
        }
    }

    @PostMapping("/")
    public ResponseEntity<ApiResponseDTO> create(@Valid @RequestBody final EventSaveDTO entity) {
        try {
            final Event event = this.eventService.save(entity);
            final EventShowResponseDTO response = this.genericMapper.toDTO(event, EventShowResponseDTO.class);
            return ResponseEntity.ok(new ApiResponseDTO("Event created", response));
        } catch (final Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponseDTO(e.getMessage(), null));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDTO> update(@PathVariable final Long id, @Valid @RequestBody final EventSaveDTO entity) {
        try {
            final Event event = this.eventService.update(id, entity);
            final EventShowResponseDTO response = this.genericMapper.toDTO(event, EventShowResponseDTO.class);
            return ResponseEntity.ok(new ApiResponseDTO("Event updated", response));
        } catch (final Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponseDTO(e.getMessage(), null));
        }
    }

}
