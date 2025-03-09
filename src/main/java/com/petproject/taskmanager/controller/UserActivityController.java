package com.petproject.taskmanager.controller;

import com.petproject.taskmanager.dto.UserActivityDto;
import com.petproject.taskmanager.service.UserActivityService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users/activities")
@RequiredArgsConstructor
public class UserActivityController {

    private final UserActivityService userActivityService;

    @GetMapping
    public ResponseEntity<List<UserActivityDto>> getUserActivities() {
        return ResponseEntity.ok(userActivityService.getUserActivities());
    }
}
