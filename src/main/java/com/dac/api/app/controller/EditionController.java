package com.dac.api.app.controller;

import com.dac.api.app.dto.ApiResponseDTO;
import com.dac.api.app.dto.EditionResponseDTO;
import com.dac.api.app.dto.EditionSaveDTO;
import com.dac.api.app.model.Edition;
import com.dac.api.app.service.impl.EditionServiceImpl;
import com.dac.api.app.util.GenericMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Tag(name = "Editions endpoints")
@RestController
@RequestMapping("/api/editions")
@RequiredArgsConstructor
public class EditionController {

    private final EditionServiceImpl editionService;
    private final GenericMapper genericMapper;

    @GetMapping("/")
    public ResponseEntity<ApiResponseDTO> index() {
        try {
            final List<Edition> editions = this.editionService.findAll();
            final List<EditionResponseDTO> response = editions.stream()
                    .map(edition -> this.genericMapper.toDTO(edition, EditionResponseDTO.class))
                    .collect(Collectors.toList());
            return ResponseEntity.ok(new ApiResponseDTO("List of editions", response));
        } catch (final Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponseDTO(e.getMessage(), null));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO> show(@PathVariable final Long id) {
        try {
            final Optional<Edition> edition = this.editionService.findById(id);
            return ResponseEntity.ok().build();
        } catch (final Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponseDTO(e.getMessage(), null));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDTO> delete(@PathVariable final Long id) {
        try {
            this.editionService.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (final Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponseDTO(e.getMessage(), null));
        }
    }

    @PostMapping("/")
    public ResponseEntity<ApiResponseDTO> create(@Valid @RequestBody final EditionSaveDTO entity) {
        try {
            final Edition edition = this.editionService.save(entity);
            return ResponseEntity.ok().build();
        } catch (final Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponseDTO(e.getMessage(), null));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDTO> update(@PathVariable final Long id, @Valid @RequestBody final EditionSaveDTO entity) {
        try {
            final Edition edition = this.editionService.update(id, entity);
            return ResponseEntity.ok().build();
        } catch (final Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponseDTO(e.getMessage(), null));
        }
    }

    @PatchMapping("/{id}/organizer/{organizer_id}")
    @Operation(description = "Endpoint para adição de organizador.")
    public ResponseEntity<ApiResponseDTO> updateOrganizer(@PathVariable final Long id, @PathVariable final Long organizer_id) {
        try {
            final Edition edition = this.editionService.updateOrganizer(id, organizer_id);
            return ResponseEntity.ok().build();
        } catch (final Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponseDTO(e.getMessage(), null));
        }
    }

}
