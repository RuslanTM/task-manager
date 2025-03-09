package com.petproject.taskmanager.service;

import com.petproject.taskmanager.data.entity.UserActivityEntity;
import com.petproject.taskmanager.data.enums.UserActivityType;
import com.petproject.taskmanager.data.repository.UserActivityRepository;
import com.petproject.taskmanager.dto.UserActivityDto;
import com.petproject.taskmanager.dto.UserDto;
import com.petproject.taskmanager.mapper.TaskMapper;
import java.time.Instant;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;

@Service
@RequiredArgsConstructor
public class UserActivityService {

    private final UserActivityRepository userActivityRepository;
    private final UserService userService;
    private final TaskMapper taskMapper;

    @Transactional
    public void createUserActivity(String activityName, UserActivityType userActivityType, String description) {
        //TODO: Get the currentUser after adding authentication
        UserDto userDto = userService.findAll().get(0);
        String sessionId = RequestContextHolder.currentRequestAttributes().getSessionId();

        var userActivity = UserActivityEntity.builder()
            .userId(userDto.id())
            .eventTime(Instant.now())
            .activityName(activityName)
            .activityType(userActivityType)
            .description(description)
            .sessionId(sessionId)
            .build();

        userActivityRepository.save(userActivity);
    }

    @Transactional(readOnly = true)
    public List<UserActivityDto> getUserActivities() {
        //TODO: Get the currentUser after adding authentication
        UserDto userDto = userService.findAll().get(0);

        return userActivityRepository.findByUserIdOrderByEventTimeAsc(userDto.id())
            .stream().map(taskMapper::toUserActivityDto)
            .toList();
    }
}
