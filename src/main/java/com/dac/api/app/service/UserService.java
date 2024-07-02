package com.dac.api.app.service;

import com.dac.api.app.dto.UserSaveDTO;
import com.dac.api.app.enums.UserRole;
import com.dac.api.app.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> findAll();
    User save(UserSaveDTO data);
    Optional<User> findById(Long id);
    User update(Long id, UserSaveDTO data);
    void deleteById(Long id);

    void deleteBySelf();

    void grantAuthority(Long userId, UserRole newRole);

    User updateFavoriteActivity(Long id, Long activity_id);
}
