package com.petproject.taskmanager.dto;

import com.petproject.taskmanager.data.enums.TaskStatusEnum;
import java.time.Instant;
import java.time.LocalDate;

public record TaskDto(
    String id,
    String title,
    String description,
    LocalDate dueDate,
    Instant createdDate,
    Instant executionDate,
    String authorId,
    String executorId,
    TaskStatusEnum status
) {

    public TaskDto(String id, String title, String description, LocalDate dueDate, Instant createdDate,
        Instant executionDate, String authorId, String executorId, TaskStatusEnum status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.createdDate = createdDate;
        this.executionDate = executionDate;
        this.authorId = authorId;
        this.executorId = executorId;
        this.status = status;
    }
}
