package com.dac.api.app.controller;

import com.dac.api.app.dto.ApiResponseDTO;
import com.dac.api.app.dto.UserResponseDTO;
import com.dac.api.app.dto.UserSaveDTO;
import com.dac.api.app.dto.UserShowResponseDTO;
import com.dac.api.app.model.User;
import com.dac.api.app.service.impl.UserServiceImpl;
import com.dac.api.app.util.GenericMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Tag(name = "User endpoints")
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userService;
    private final GenericMapper genericMapper;

    @GetMapping("/")
    @Operation(description = "Endpoint para listagem de usuários.")
    public ResponseEntity<ApiResponseDTO> index() {
        try {
            final List<User> users = this.userService.findAll();
            final List<UserResponseDTO> usersResponse = users.stream()
                    .map(user -> this.genericMapper.toDTO(user, UserResponseDTO.class))
                    .collect(Collectors.toList());

            return ResponseEntity.ok(new ApiResponseDTO("List of users", usersResponse));
        } catch (final Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponseDTO(e.getMessage(), null));
        }
    }

    @GetMapping("/{id}")
    @Operation(description = "Endpoint para exibição de usuários.")
    public ResponseEntity<ApiResponseDTO> show(@PathVariable final Long id) {
        try {
            final Optional<User> user = this.userService.findById(id);

            final UserShowResponseDTO response = this.genericMapper.toDTO(user, UserShowResponseDTO.class);

            return ResponseEntity.ok(new ApiResponseDTO("Show user", response));
        } catch (final Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponseDTO(e.getMessage(), null));
        }
    }

    @PostMapping("/")
    @Operation(description = "Endpoint para criação de usuários.")
    @Secured("ADMIN")
    public ResponseEntity<ApiResponseDTO> create(@Valid @RequestBody final UserSaveDTO entity) {
        try {
            final User user = this.userService.save(entity);
            final UserShowResponseDTO response = this.genericMapper.toDTO(user, UserShowResponseDTO.class);
            return ResponseEntity.ok(new ApiResponseDTO("User created", response));
        } catch (final Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponseDTO(e.getMessage(), null));
        }
    }

    @PutMapping("/{id}")
    @Operation(description = "Endpoint para atualização de usuários.")
    public ResponseEntity<ApiResponseDTO> update(@PathVariable final Long id, @Valid @RequestBody final UserSaveDTO entity) {
        try {
            final User user = this.userService.update(id, entity);
            final UserShowResponseDTO response = this.genericMapper.toDTO(user, UserShowResponseDTO.class);
            return ResponseEntity.ok(new ApiResponseDTO("User updated", response));
        } catch (final Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponseDTO(e.getMessage(), null));
        }
    }

    @DeleteMapping("/{id}")
    @Operation(description = "Endpoint para remoção de usuários.")
    public ResponseEntity<ApiResponseDTO> delete(@PathVariable final Long id) {
        try {
            this.userService.deleteById(id);
            return ResponseEntity.ok(new ApiResponseDTO("User deleted", null));
        } catch (final Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponseDTO(e.getMessage(), null));
        }
    }

    @PatchMapping("/{id}/activity/{activity_id}")
    @Operation(description = "Endpoint para favoritar atividades.")
    public ResponseEntity<ApiResponseDTO> updateFavoriteActivity(@PathVariable final Long id,
            @PathVariable final Long activity_id) {
        try {
            final User user = this.userService.updateFavoriteActivity(id, activity_id);
            final UserShowResponseDTO response = this.genericMapper.toDTO(user, UserShowResponseDTO.class);
            return ResponseEntity.ok(new ApiResponseDTO("Activity favorited", response));
        } catch (final Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponseDTO(e.getMessage(), null));
        }
    }
}