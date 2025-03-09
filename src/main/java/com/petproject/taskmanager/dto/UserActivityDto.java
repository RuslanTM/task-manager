package com.petproject.taskmanager.dto;

import java.time.Instant;

public record UserActivityDto(String userId,
                              Instant eventTime,
                              String sessionId,
                              String activityName,
                              String activityType,
                              String description) {

}
