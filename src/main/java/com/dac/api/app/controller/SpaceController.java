package com.dac.api.app.controller;

import com.dac.api.app.dto.ApiResponseDTO;
import com.dac.api.app.dto.SpaceResponseDTO;
import com.dac.api.app.dto.SpaceSaveDTO;
import com.dac.api.app.dto.SpaceShowResponseDTO;
import com.dac.api.app.model.Space;
import com.dac.api.app.service.impl.SpaceServiceImpl;
import com.dac.api.app.util.GenericMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Tag(name = "Space endpoints")
@RestController
@RequestMapping("/api/spaces")
public class SpaceController {

    @Autowired
    private SpaceServiceImpl spaceService;

    @Autowired
    private GenericMapper genericMapper;

    @GetMapping("/")
    public ResponseEntity<ApiResponseDTO> index() {
        try {
            final List<Space> spaces = this.spaceService.findAll();
            final List<SpaceResponseDTO> response = spaces.stream()
                    .map(space -> this.genericMapper.toDTO(space, SpaceResponseDTO.class))
                    .collect(Collectors.toList());
            return ResponseEntity.ok(new ApiResponseDTO("List of spaces", response));
        } catch (final Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponseDTO(e.getMessage(), null));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO> show(@PathVariable final Long id) {
        try {
            final Optional<Space> space = this.spaceService.findById(id);
            final SpaceShowResponseDTO response = this.genericMapper.toDTO(space, SpaceShowResponseDTO.class);
            return ResponseEntity.ok(new ApiResponseDTO("Show space", response));
        } catch (final Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponseDTO(e.getMessage(), null));
        }
    }

    @DeleteMapping("/{id}/edition/{editionId}")
    public ResponseEntity<ApiResponseDTO> delete(@PathVariable final Long id, @PathVariable final Long editionId) {
        try {
            this.spaceService.deleteById(id, editionId);
            return ResponseEntity.ok(new ApiResponseDTO("Space deleted", null));
        } catch (final Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponseDTO(e.getMessage(), null));
        }
    }

    @PostMapping("/{editionId}")
    public ResponseEntity<ApiResponseDTO> create(@PathVariable final Long editionId,
            @Valid @RequestBody final SpaceSaveDTO entity) {
        try {
            final Space space = this.spaceService.save(editionId, entity);
            final SpaceShowResponseDTO response = this.genericMapper.toDTO(space, SpaceShowResponseDTO.class);
            return ResponseEntity.ok(new ApiResponseDTO("Space created", response));
        } catch (final Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponseDTO(e.getMessage(), null));
        }
    }

    @PutMapping("/{id}/edition/{editionId}")
    public ResponseEntity<ApiResponseDTO> update(@PathVariable final Long id, @Valid @PathVariable final Long editionId,
                                                 @Valid @RequestBody final SpaceSaveDTO entity) {
        try {
            final Space space = this.spaceService.update(id, editionId, entity);
            final SpaceShowResponseDTO response = this.genericMapper.toDTO(space, SpaceShowResponseDTO.class);
            return ResponseEntity.ok(new ApiResponseDTO("Space updated", response));
        } catch (final Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponseDTO(e.getMessage(), null));
        }
    }
}
