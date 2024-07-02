package com.dac.api.app.service.impl;

import com.dac.api.app.dto.UserSaveDTO;
import com.dac.api.app.exception.ActivityNotFoundException;
import com.dac.api.app.exception.UserFoundException;
import com.dac.api.app.exception.UserNotFoundException;
import com.dac.api.app.model.Activity;
import com.dac.api.app.model.User;
import com.dac.api.app.repository.ActivityRepository;
import com.dac.api.app.repository.UserRepository;
import com.dac.api.app.service.UserService;
import com.dac.api.app.util.GenericMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ActivityRepository activityRepository;
    private final GenericMapper genericMapper;

    @Override
    public List<User> findAll() {
        return this.userRepository.findAll();
    }

    @Override
    public User save(final UserSaveDTO data) {

        this.userRepository
                .findByUsernameOrEmail(data.getUsername(), data.getEmail())
                .ifPresent(user -> {
                    throw new UserFoundException();
                });

        final var password = data.getPassword();
        data.setPassword(password);

        final User user = genericMapper.toEntity(data, User.class);
        return this.userRepository.save(user);
    }

    @Override
    public Optional<User> findById(final Long id) {
        final User user = this.userRepository.getReferenceById(id);
        return Optional.of(user);
    }

    @Override
    public User update(final Long id, final UserSaveDTO data) {
        final User user = this.userRepository.getReferenceById(id);

        if (user == null)
            return null;

        if (data.getEmail() != null)
            user.setEmail(data.getEmail());
        if (data.getUsername() != null)
            user.setUsername(data.getUsername());
        if (data.getPassword() != null) {
            final var password = data.getPassword();
            user.setPassword(password);
        }

        return this.userRepository.save(user);
    }

    @Override
    public void deleteById(final Long id) {
        this.userRepository.deleteById(id);
    }

    @Override
    public User updateFavoriteActivity(final Long id, final Long activity_id) {
        List<Activity> activities = new ArrayList<Activity>();

        final User user = this.userRepository.findById(id).orElseThrow(
                () -> {
                    throw new UserNotFoundException();
                });

        activities = user.getFavoritedActivities();

        final Activity activity = this.activityRepository.findById(activity_id).orElseThrow(
                () -> {
                    throw new ActivityNotFoundException();
                });

        if (activities.contains(activity))
            activities.remove(activity);
        else
            activities.add(activity);

        user.setFavoritedActivities(activities);

        return this.userRepository.save(user);
    }
}
